package com.example.submission01.data.source.remote

import com.example.submission01.data.source.remote.response.DataTvResponse


class ApiResponse<T>(val status: StatusResponse, val body : T, val message : String? ) {
    companion object {
        fun <T> success(body: MutableList<T>): ApiResponse<List<T>> = ApiResponse(StatusResponse.SUCCESS, body, null)
        fun <T> empty(msg:String, body : List<T>): ApiResponse<List<T>> = ApiResponse(StatusResponse.EMPTY, body, msg)
        fun <T> error(msg:String, body : List<T>): ApiResponse<List<T>> = ApiResponse(StatusResponse.ERROR, body, msg)
    }
}