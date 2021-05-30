package com.example.submission01.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submission01.BuildConfig
import com.example.submission01.data.DataMovieResponse
import com.example.submission01.data.source.remote.api.ApiService
import com.example.submission01.data.source.remote.response.DataTvResponse
import com.example.submission01.utils.EspressoIdlingResource
import retrofit2.*
import java.lang.Exception

class RemoteDataSource(private val apiService : ApiService) {

    suspend fun getNMovies(n : Int, specificId : Int = -1) : LiveData<ApiResponse<List<DataMovieResponse>>> {
        val resultResponse = MutableLiveData<ApiResponse<List<DataMovieResponse>>>()
        val arrReturnValue : MutableList<DataMovieResponse> = ArrayList()
        if(specificId == -1){
            val startIndex = 120
            val endIndex = 120 + n
            for (id in startIndex until endIndex) {
                EspressoIdlingResource.increment()
                try {
                    arrReturnValue.add(apiService.getNMovies(id.toString(), BuildConfig.API_KEY).await())
                } catch (e : Exception) {
                    Log.e("GET MOVIE", "Id tidak ada")
                } finally {
                    EspressoIdlingResource.decrement()
                }

            }

        } else {
            EspressoIdlingResource.increment()
            arrReturnValue.add(apiService.getNMovies(specificId.toString(), BuildConfig.API_KEY).await())
            EspressoIdlingResource.decrement()
        }
        resultResponse.value = ApiResponse.success(arrReturnValue)
        return resultResponse
    }

    suspend fun getNSeries(n : Int, specificId : Int = -1) : LiveData<ApiResponse<List<DataTvResponse>>> {
        val resultResponse = MutableLiveData<ApiResponse<List<DataTvResponse>>>()
        val arrReturnValue : MutableList<DataTvResponse> = ArrayList()
        if(specificId == -1){
            val startIndex = 120
            val endIndex = 120 + n
            for (id in startIndex until endIndex) {
                EspressoIdlingResource.increment()
                try{
                    arrReturnValue.add(apiService.getNTv(id.toString(), BuildConfig.API_KEY).await())
                } catch (e : Exception) {
                    Log.e("GET SERIES", "id tidak ditemukan")
                } finally {
                    EspressoIdlingResource.decrement()
                }

            }
        } else {
            EspressoIdlingResource.increment()
            arrReturnValue.add(apiService.getNTv(specificId.toString(), BuildConfig.API_KEY).await())
            EspressoIdlingResource.decrement()
        }
        resultResponse.value = ApiResponse.success(arrReturnValue)
        return resultResponse
    }

}