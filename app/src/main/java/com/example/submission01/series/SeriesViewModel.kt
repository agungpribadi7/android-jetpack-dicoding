package com.example.submission01.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission01.data.source.ItemRepository
import com.example.submission01.data.source.local.DataClass
import kotlinx.coroutines.launch

class SeriesViewModel(private val repository: ItemRepository) : ViewModel() {
    private val _series = MutableLiveData<ArrayList<DataClass>>()
    val series : LiveData<ArrayList<DataClass>> = _series

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading : LiveData<Boolean> = _isLoading

    fun getData() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.getNTv(10)
            _series.value = response
            _isLoading.value = false
        }
    }

    fun getDataById(id : Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.getNTv(1, id)
            _series.value = response
            _isLoading.value = false
        }
    }
}