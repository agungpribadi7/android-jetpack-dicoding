package com.example.submission01.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.vo.Resource

interface ItemDataSource {
    fun getNMovies(n : Int, specificId: Int = -1): LiveData<Resource<PagedList<DataEntity>>>
    fun getNTv(n : Int, specificId: Int = -1): LiveData<Resource<PagedList<DataEntity>>>
    fun getFavoriteMovies(sort : String) : LiveData<Resource<List<DataEntity>>>
    fun getFavoriteSeries(sort : String) : LiveData<Resource<List<DataEntity>>>
    fun addFavoriteMovie(id : Int) : Resource<String>
    fun addFavoriteSeries(id : Int) : Resource<String>
    fun deleteFavoriteMovie(id : Int) : Resource<String>
    fun deleteFavoriteSeries(id : Int) : Resource<String>
}