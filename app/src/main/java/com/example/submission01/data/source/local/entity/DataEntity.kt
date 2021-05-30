package com.example.submission01.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "data", primaryKeys = ["id", "movie_or_series"],)
data class DataEntity(
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "image")
    val image: String?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "directors")
    val directors: String?,

    @ColumnInfo(name = "release_year")
    val releaseYear: String?,

    @ColumnInfo(name = "rating")
    val rating: String?,

    @ColumnInfo(name = "duration")
    val duration: String?,

    @ColumnInfo(name = "genre")
    val genre: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "movie_or_series")
    val movieOrSeries: Int = 0,

    @ColumnInfo(name = "favorite")
    val favorite : Boolean = false
    ) : Parcelable
