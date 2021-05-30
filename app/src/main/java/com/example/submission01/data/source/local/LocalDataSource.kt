package com.example.submission01.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.data.source.local.room.DataDao

class LocalDataSource(private val mDataDao : DataDao) {

    fun getAllMovies() : DataSource.Factory<Int, DataEntity> = mDataDao.getAllMovies()

    fun getAllSeries() : DataSource.Factory<Int, DataEntity> = mDataDao.getAllSeries()

    fun getFavoriteMovies(query: SimpleSQLiteQuery) : List<DataEntity> = mDataDao.getFavoriteMovies(query)

    fun getFavoriteSeries(query: SimpleSQLiteQuery) : List<DataEntity> = mDataDao.getFavoriteSeries(query)

    fun getDetailMovie(movieId : Int) = mDataDao.getDetailMovie(movieId)

    fun getDetailSeries(seriesId : Int) = mDataDao.getDetailSeries(seriesId)

    fun deleteFavoriteMovie(movieId: Int) = mDataDao.deleteFavoriteMovie(movieId)

    fun deleteFavoriteSeries(seriesId: Int) = mDataDao.deleteFavoriteSeries(seriesId)

    fun addFavoriteMovie(movieId: Int) = mDataDao.addFavoriteMovie(movieId)

    fun addFavoriteSeries(seriesId: Int) = mDataDao.addFavoriteSeries(seriesId)

    fun insertData(data : List<DataEntity>) = mDataDao.insertData(data)
}