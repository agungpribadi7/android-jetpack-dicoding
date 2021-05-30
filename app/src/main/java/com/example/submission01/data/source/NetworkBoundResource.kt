package com.example.submission01.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.submission01.data.source.remote.ApiResponse
import com.example.submission01.data.source.remote.StatusResponse
import com.example.submission01.utils.AppExecutors
import com.example.submission01.utils.EspressoIdlingResource
import com.example.submission01.vo.Resource
import kotlinx.coroutines.*

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if(shouldFetch(data)) {
                val serviceJob = Job()
                val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
                serviceScope.launch {
                    EspressoIdlingResource.increment()
                    fetchFromNetwork(dbSource)
                    EspressoIdlingResource.decrement()
                }
            } else {
                EspressoIdlingResource.increment()
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
                EspressoIdlingResource.decrement()
            }
        }
    }

    private fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private suspend fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        EspressoIdlingResource.increment()
        result.addSource(dbSource) { newData ->
            result.value = Resource.loading(newData)
        }
        EspressoIdlingResource.decrement()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            EspressoIdlingResource.increment()
            when(response.status) {
                StatusResponse.SUCCESS ->
                    mExecutors.diskIO().execute {
                        saveCallResult(response.body)
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) {newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }
                StatusResponse.EMPTY -> mExecutors.mainThread().execute{
                    result.addSource(loadFromDB()) {newData ->
                        result.value = Resource.success(newData)
                    }
                }
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.error(response.message, newData)
                    }
                }
            }
        }
        EspressoIdlingResource.decrement()
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}