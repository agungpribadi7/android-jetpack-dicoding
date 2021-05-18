package com.example.submission01.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission01.data.source.ItemRepository
import com.example.submission01.data.source.local.DataClass
import kotlinx.coroutines.launch

class MoviesViewModel(val repository : ItemRepository) : ViewModel() {
    private val _movies = MutableLiveData<ArrayList<DataClass>>()
    val movies : LiveData<ArrayList<DataClass>> = _movies

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            val responseResult = repository.getNMovies(10)
            _movies.value = responseResult
            _isLoading.value = false
        }

    }

    fun getMoviesById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val responseResult = repository.getNMovies(1, id)
            _movies.value = responseResult
            _isLoading.value = false
        }
    }


}