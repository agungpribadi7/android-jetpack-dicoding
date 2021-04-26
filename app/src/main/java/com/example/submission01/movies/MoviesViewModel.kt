package com.example.submission01.movies

import androidx.lifecycle.ViewModel
import com.example.submission01.data.Data
import com.example.submission01.data.DataClass

class MoviesViewModel : ViewModel() {
    private lateinit var idData: String

    fun setIdData(id : String){
        idData = id
    }

    fun getData(): MutableList<DataClass> {
        return Data.getMovies()
    }

    fun getDataById() : DataClass?{
        val data = getData()
        var result : DataClass? = null
        for(item in data){
            if(item.id == idData.toInt()){
                result = item
            }
        }
        return result
    }
}