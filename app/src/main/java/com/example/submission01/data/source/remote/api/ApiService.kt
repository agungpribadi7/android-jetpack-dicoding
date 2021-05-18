package com.example.submission01.data.source.remote.api
import com.example.submission01.data.DataMovieResponse
import com.example.submission01.data.source.remote.response.DataTvResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("movie/{id}}")
    fun getNMovies(
        @Path("id") id: String,
        @Query("api_key") api: String
    ): Call<DataMovieResponse>

    @GET("tv/{id}")
    fun getNTv(
        @Path("id") id: String,
        @Query("api_key") api: String
    ): Call<DataTvResponse>


}