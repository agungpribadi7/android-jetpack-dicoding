package com.example.submission01.utils

import androidx.sqlite.db.SimpleSQLiteQuery
import java.lang.StringBuilder

object SortUtils {
    const val ID = "Id"
    const val TITLE = "Title"

    fun getSortedQuery(filter: String, tipe : Int) :SimpleSQLiteQuery {
        val simpleQuery = StringBuilder()
        if(tipe == 0){
            simpleQuery.append("SELECT * FROM data where favorite = 1 AND movie_or_series = 0 ")
        } else {
            simpleQuery.append("SELECT * FROM data where favorite = 1 AND movie_or_series = 1 ")
        }

        if(filter == ID) {
            simpleQuery.append("ORDER BY ID ASC")
        } else if(filter == TITLE) {
            simpleQuery.append("ORDER BY TITLE ASC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}