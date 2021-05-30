package com.example.submission01.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.submission01.data.source.local.entity.DataEntity

@Dao
interface DataDao {
    @Query("SELECT * FROM data WHERE movie_or_series = 0")
    fun getAllMovies() : DataSource.Factory<Int, DataEntity>

    @Query("SELECT * FROM data WHERE movie_or_series = 1")
    fun getAllSeries() : DataSource.Factory<Int, DataEntity>

    @RawQuery(observedEntities = [DataEntity::class])
    fun getFavoriteMovies(query: SupportSQLiteQuery) : List<DataEntity>

    @RawQuery(observedEntities = [DataEntity::class])
    fun getFavoriteSeries(query: SupportSQLiteQuery) : List<DataEntity>

    @Query("SELECT * FROM data where movie_or_series = 0 AND id = :movieId")
    fun getDetailMovie(movieId : Int) : DataSource.Factory<Int, DataEntity>

    @Query("SELECT * FROM data where movie_or_series = 1 AND id = :seriesId")
    fun getDetailSeries(seriesId : Int) : DataSource.Factory<Int, DataEntity>

    @Query("UPDATE data set favorite = 0 where id = :movieId AND movie_or_series = 0")
    fun deleteFavoriteMovie(movieId: Int)

    @Query("UPDATE data set favorite = 0 where id = :seriesId AND movie_or_series = 1")
    fun deleteFavoriteSeries(seriesId: Int)

    @Query("UPDATE data set favorite = 1 where id = :movieId AND movie_or_series = 0")
    fun addFavoriteMovie(movieId: Int)

    @Query("UPDATE data set favorite = 1 where id = :seriesId AND movie_or_series = 1")
    fun addFavoriteSeries(seriesId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: List<DataEntity>)

}