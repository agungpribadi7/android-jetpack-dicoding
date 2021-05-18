package com.example.submission01.data.source.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataClass(val id: Int?, val image: String?, val title: String?, val directors: String?, val releaseYear: String?, val rating: String?, val duration: String?, val genre: String?, val description: String?) : Parcelable
