package com.example.submission01.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataClass(val id: Int?, val image: String?, val title: String?, val directors: String?, val releaseYear: Int, val rating: String?, val duration: String?, val genre: String?, val description: String?) : Parcelable
