package com.example.submission01.data.source.remote

import com.example.submission01.BuildConfig
import com.example.submission01.data.DataMovieResponse
import com.example.submission01.data.source.remote.api.ApiConfig
import com.example.submission01.data.source.remote.response.DataTvResponse
import com.example.submission01.utils.EspressoIdlingResource
import retrofit2.*

class RemoteDataSource {

    companion object {
        @Volatile
        private  var instance: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    suspend fun getNMovies(n : Int, specificId : Int = -1, callback : LoadMovieApi) {
        if(specificId == -1){
            for (id in 120 until 120 + n) {
                EspressoIdlingResource.increment()
                ApiConfig.getApiService().getNMovies(id.toString(), BuildConfig.API_KEY).await().let {
                        singleMovie -> callback.responsesRetrieved(singleMovie)
                }
                EspressoIdlingResource.decrement()
            }
        } else {
            EspressoIdlingResource.increment()
            ApiConfig.getApiService().getNMovies(specificId.toString(), BuildConfig.API_KEY).await().let {
                    singleMovie -> callback.responsesRetrieved(singleMovie)
            }
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getNSeries(n : Int, specificId : Int = -1, callback: LoadSeriesApi) {
        if(specificId == -1){
            for (id in 121 until 121 + n) {
                EspressoIdlingResource.increment()
                ApiConfig.getApiService().getNTv(id.toString(), BuildConfig.API_KEY).await().let {
                        singleTv -> callback.responsesRetrived(singleTv)
                }
                EspressoIdlingResource.decrement()
            }
        } else {
            EspressoIdlingResource.increment()
            ApiConfig.getApiService().getNTv(specificId.toString(), BuildConfig.API_KEY).await().let {
                    singleTv -> callback.responsesRetrived(singleTv)
            }
            EspressoIdlingResource.decrement()
        }
    }

    interface LoadMovieApi {
        fun responsesRetrieved(movieResponse : DataMovieResponse)
    }

    interface LoadSeriesApi {
        fun responsesRetrived(seriesResponse : DataTvResponse)
    }

}