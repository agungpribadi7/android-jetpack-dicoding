package com.example.submission01.data.source

import android.util.Log
import com.example.submission01.data.DataMovieResponse
import com.example.submission01.data.source.local.DataClass
import com.example.submission01.data.source.remote.RemoteDataSource
import com.example.submission01.data.source.remote.response.DataTvResponse

class ItemRepository private constructor (private val remote : RemoteDataSource) : ItemDataSource {
    companion object {
        @Volatile
        private var instance: ItemRepository? = null

        fun getInstance(remoteData : RemoteDataSource) : ItemRepository =
            instance ?: synchronized(this) {
                instance ?: ItemRepository(remoteData).apply { instance = this }
            }
    }


    private val _baseImageLink = "https://image.tmdb.org/t/p/w500"
    override suspend fun getNMovies(n : Int, specificId : Int) : ArrayList<DataClass> {
        val listMovie : ArrayList<DataClass> = ArrayList()
        val resultResponse: MutableList<DataMovieResponse?> = if(specificId == -1){
            val arrReturnValue : MutableList<DataMovieResponse?> = ArrayList()
            remote.getNMovies(n, callback = object : RemoteDataSource.LoadMovieApi {
                override fun responsesRetrieved(movieResponse: DataMovieResponse) {
                    arrReturnValue.add(movieResponse)
                }
            })
            arrReturnValue
        } else {
            val arrReturnValue : MutableList<DataMovieResponse?> = ArrayList()
            remote.getNMovies(n, specificId, callback = object : RemoteDataSource.LoadMovieApi {
                override fun responsesRetrieved(movieResponse: DataMovieResponse) {
                    arrReturnValue.add(movieResponse)
                }
            })
            arrReturnValue
        }

        for(result in resultResponse){
            val id = result?.id
            val image :String = if(result?.posterPath != "" || result.posterPath.isNotEmpty())  {
                _baseImageLink + result?.posterPath
            } else {
                ""
            }
            val title = result?.originalTitle
            val directors = result?.productionCompanies
            var directorsName = "-"
            if(directors?.isNotEmpty() == true){
                directorsName = directors[0]?.name.toString()
            }
            val releaseYear = result?.releaseDate
            val rating :String = result?.voteAverage.toString() + " ("+ result?.voteCount.toString() +")"
            val duration = result?.runtime.toString() + " minutes"
            var genre: String
            var futureGenre = ""
            if(result?.genres?.isNotEmpty() == true){
                for(genreResponse in result.genres){
                    if (genreResponse != null && genreResponse.name?.isNotEmpty() == true) {
                        futureGenre += genreResponse.name + ", "
                    }
                }
            }
            genre = if(futureGenre != "") {
                futureGenre
            } else {
                "-"
            }
            val description = result?.overview.toString()
            val singleItem = DataClass(id, image, title, directorsName, releaseYear, rating, duration, genre, description)
            listMovie.add(singleItem)
        }
        return listMovie
    }

    override suspend fun getNTv(n : Int, specificId : Int) : ArrayList<DataClass> {
        val listTv : ArrayList<DataClass> = ArrayList()
        val resultResponse: MutableList<DataTvResponse?> = if(specificId == -1){
            val arrReturnValue : MutableList<DataTvResponse?> = ArrayList()
            remote.getNSeries(n, callback = object : RemoteDataSource.LoadSeriesApi {
                override fun responsesRetrived(seriesResponse: DataTvResponse) {
                    arrReturnValue.add(seriesResponse)
                }
            })
            arrReturnValue
        } else {
            val arrReturnValue : MutableList<DataTvResponse?> = ArrayList()
            remote.getNSeries(n, specificId, callback = object : RemoteDataSource.LoadSeriesApi {
                override fun responsesRetrived(seriesResponse: DataTvResponse) {
                    arrReturnValue.add(seriesResponse)
                }
            })
            arrReturnValue
        }

        for(result in resultResponse){
            val id = result?.id
            val image: String =
                if(result?.posterPath.toString() != "" || result?.posterPath.toString().isNotEmpty())  {
                _baseImageLink + result?.posterPath
            } else {
                ""
            }
            val title = result?.originalName
            val directors = result?.productionCompanies
            var directorsName = "-"
            if(directors?.isNotEmpty() == true){
                directorsName = directors[0]?.name.toString()
            }
            val releaseYear = result?.firstAirDate
            val rating :String = result?.voteAverage.toString() + " ("+ result?.voteCount.toString() +")"
            val duration = result?.numberOfSeasons.toString() + " Seasons"
            var genre: String
            var futureGenre = ""
            if(result?.genres?.isNotEmpty() == true){
                for(genreResponse in result.genres){
                    if (genreResponse != null && genreResponse.name?.isNotEmpty() == true) {
                        futureGenre += genreResponse.name + ", "
                    }
                }
            }
            genre = if(futureGenre != "") {
                futureGenre
            } else {
                "-"
            }
            val description = result?.overview.toString()
            val singleItem = DataClass(id, image, title, directorsName, releaseYear, rating, duration, genre, description)
            listTv.add(singleItem)
        }
        return listTv
    }
}