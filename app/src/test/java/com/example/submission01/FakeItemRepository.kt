package com.example.submission01

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.submission01.data.DataMovieResponse
import com.example.submission01.data.source.ItemDataSource
import com.example.submission01.data.source.NetworkBoundResource
import com.example.submission01.data.source.local.LocalDataSource
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.data.source.remote.ApiResponse
import com.example.submission01.data.source.remote.RemoteDataSource
import com.example.submission01.data.source.remote.response.DataTvResponse
import com.example.submission01.utils.AppExecutors
import com.example.submission01.utils.SortUtils
import com.example.submission01.vo.Resource

class FakeItemRepository(
    private val remote : RemoteDataSource,
    private val localDataSource : LocalDataSource,
    private val appExecutors: AppExecutors
) : ItemDataSource {

    private val _baseImageLink = "https://image.tmdb.org/t/p/w500"
    override fun getNMovies(n : Int, specificId : Int) : LiveData<Resource<PagedList<DataEntity>>> {
        return object  : NetworkBoundResource<PagedList<DataEntity>, List<DataMovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<DataEntity>> {

                return if(specificId != -1) {
                    val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(1)
                        .setPageSize(1)
                        .build()
                    LivePagedListBuilder(localDataSource.getDetailMovie(specificId), config).build()
                } else {
                    val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()
                    LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
                }
            }

            override fun shouldFetch(data: PagedList<DataEntity>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): LiveData<ApiResponse<List<DataMovieResponse>>> {
                return if(specificId == -1) {
                    remote.getNMovies(n)
                } else {
                    remote.getNMovies(n, specificId)
                }
            }

            override fun saveCallResult(data: List<DataMovieResponse>) {
                val dataList = ArrayList<DataEntity>()

                for(response in data) {
                    var directorsName = "-"
                    val directors = response.productionCompanies
                    if(directors?.isNotEmpty() == true){
                        directorsName = directors[0]?.name.toString()
                    }
                    var genre: String
                    var futureGenre = ""
                    response.genres?.forEach { genreResponse ->
                        if (genreResponse != null && genreResponse.name?.isNotEmpty() == true) {
                            futureGenre += genreResponse.name + ", "
                        }
                    }
                    genre = if(futureGenre != "") {
                        futureGenre
                    } else {
                        "-"
                    }
                    val urlImage: String = if(response.posterPath.isNullOrEmpty() && response.posterPath.toString() != "null") {
                        "https://i.ibb.co/FkpWQ4w/nodataflutterprojek.png"
                    } else {
                        _baseImageLink+response.posterPath
                    }

                    val item = DataEntity(response.id,
                        urlImage,
                        response.originalTitle,
                        directorsName,
                        response.releaseDate,
                        response.voteAverage.toString()+ " ("+ response.voteCount.toString() +")",
                        response.runtime.toString() + " minutes",
                        genre,
                        response.overview,
                        0
                    )
                    dataList.add(item)
                }
                localDataSource.insertData(dataList)
            }
        }.asLiveData()
    }

    override fun getNTv(n : Int, specificId : Int) : LiveData<Resource<PagedList<DataEntity>>> {
        return object  : NetworkBoundResource<PagedList<DataEntity>, List<DataTvResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<DataEntity>> {
                return if(specificId != -1) {
                    val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(1)
                        .setPageSize(1)
                        .build()
                    LivePagedListBuilder(localDataSource.getDetailSeries(specificId), config).build()
                } else {
                    val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()
                    LivePagedListBuilder(localDataSource.getAllSeries(), config).build()
                }
            }

            override fun shouldFetch(data: PagedList<DataEntity>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): LiveData<ApiResponse<List<DataTvResponse>>> {
                return if(specificId == -1) {
                    remote.getNSeries(n)
                } else {
                    remote.getNSeries(n, specificId)
                }
            }

            override fun saveCallResult(data: List<DataTvResponse>) {
                val dataList = ArrayList<DataEntity>()

                for(response in data) {
                    val id = response.id
                    val image: String =
                        if(response.posterPath.toString() != "" && response.posterPath.toString().isNotEmpty() && response.posterPath.toString() != "null")  {
                            _baseImageLink + response.posterPath
                        } else {
                            "https://i.ibb.co/FkpWQ4w/nodataflutterprojek.png"
                        }
                    val title = response.originalName
                    val directors = response.productionCompanies
                    var directorsName = "-"
                    if(directors?.isNotEmpty() == true){
                        directorsName = directors[0]?.name.toString()
                    }
                    val releaseYear = response.firstAirDate
                    val rating :String = response.voteAverage.toString() + " ("+ response.voteCount.toString() +")"
                    val duration = response.numberOfSeasons.toString() + " Seasons"
                    var genre: String
                    var futureGenre = ""
                    response.genres?.forEach { genreResponse ->
                        if (genreResponse != null && genreResponse.name?.isNotEmpty() == true) {
                            futureGenre += genreResponse.name + ", "
                        }
                    }
                    genre = if(futureGenre != "") {
                        futureGenre
                    } else {
                        "-"
                    }
                    val description = response.overview.toString()
                    val item = DataEntity(id, image, title, directorsName, releaseYear, rating, duration, genre, description, 1)

                    dataList.add(item)
                }
                localDataSource.insertData(dataList)
            }
        }.asLiveData()

    }

    override fun getFavoriteMovies(sort: String): LiveData<Resource<List<DataEntity>>> {
        val result : MutableLiveData<Resource<List<DataEntity>>> = MutableLiveData()
        try {
            val query = SortUtils.getSortedQuery(sort, 0)
            appExecutors.diskIO().execute {
                result.postValue(Resource.success(localDataSource.getFavoriteMovies(query)))
            }
        } catch (e : Throwable) {
            result.value = Resource.error(e.message, emptyList())
        }

        return result
    }

    override fun getFavoriteSeries(query : String): LiveData<Resource<List<DataEntity>>> {
        val result : MutableLiveData<Resource<List<DataEntity>>> = MutableLiveData()
        try{
            val myQuery = SortUtils.getSortedQuery(query, 1)
            appExecutors.diskIO().execute {
                result.postValue(Resource.success(localDataSource.getFavoriteSeries(myQuery)))
            }
        } catch (e : Throwable) {
            result.value = Resource.error(e.message, emptyList())
        }

        return result
    }

    override fun addFavoriteMovie(id: Int) : Resource<String> {
        appExecutors.diskIO().execute { localDataSource.addFavoriteMovie(id) }
        return Resource.success("Success")
    }

    override fun addFavoriteSeries(id: Int) : Resource<String> {
        appExecutors.diskIO().execute { localDataSource.addFavoriteSeries(id) }
        return Resource.success("Success")
    }

    override fun deleteFavoriteMovie(id: Int) : Resource<String> {
        appExecutors.diskIO().execute { localDataSource.deleteFavoriteMovie(id) }
        return Resource.success("Success")
    }

    override fun deleteFavoriteSeries(id: Int) : Resource<String> {
        appExecutors.diskIO().execute { localDataSource.deleteFavoriteSeries(id) }
        return Resource.success("Success")
    }
}