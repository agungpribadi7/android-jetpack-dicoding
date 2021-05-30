package com.example.submission01.series

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.submission01.data.source.ItemRepository
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.vo.Resource
import com.example.submission01.vo.Status
import kotlinx.coroutines.launch

class SeriesViewModel(private val repository: ItemRepository) : ViewModel() {
    var sort = MutableLiveData<String>()

    fun getData(n : Int = 30) : LiveData<Resource<PagedList<DataEntity>>> {
        return repository.getNTv(n)
    }

    fun getDataById(id : Int): LiveData<Resource<PagedList<DataEntity>>>{
        return repository.getNTv(1, id)
    }

    fun addFavorite(id : Int) : Resource<String> {
        return repository.addFavoriteSeries(id)
    }

    fun deleteFavorite(id : Int) : Resource<String> {
        return repository.deleteFavoriteSeries(id)
    }

    var getFavorite: LiveData<Resource<List<DataEntity>>> = Transformations.switchMap(sort) { value ->
        repository.getFavoriteSeries(value)
    }
}