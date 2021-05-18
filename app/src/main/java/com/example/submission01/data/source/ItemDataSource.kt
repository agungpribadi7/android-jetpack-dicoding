package com.example.submission01.data.source

import com.example.submission01.data.source.local.DataClass

interface ItemDataSource {
    suspend fun getNMovies(n : Int, specificId: Int = -1): ArrayList<DataClass>
    suspend fun getNTv(n : Int, specificId: Int = -1): ArrayList<DataClass>
}