package com.example.submission01.movies

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.submission01.data.source.ItemRepository
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.vo.Resource

class MoviesViewModel(private val repository : ItemRepository) : ViewModel() {
    val sort = MutableLiveData<String>()

    fun getMoviesViewModel(n : Int = 30) : LiveData<Resource<PagedList<DataEntity>>> {
        return repository.getNMovies(n)
    }

    fun getMoviesById(id : Int) : LiveData<Resource<PagedList<DataEntity>>> {
        return repository.getNMovies(1, id)
    }

    fun addFavorite(id : Int) : Resource<String> {
        return repository.addFavoriteMovie(id)
    }

    fun deleteFavorite(id : Int) : Resource<String> {
        return repository.deleteFavoriteMovie(id)
    }

    val getFavorites : LiveData<Resource<List<DataEntity>>> = Transformations.switchMap(sort)  { value ->
        repository.getFavoriteMovies(value)
    }


}