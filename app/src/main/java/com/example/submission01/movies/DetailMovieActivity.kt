package com.example.submission01.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission01.R
import com.example.submission01.data.source.local.DataClass
import com.example.submission01.databinding.ActivityDetailMovieBinding
import com.example.submission01.viewmodel.ViewModelFactory

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailMovieBinding
    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Submission 2"

        val factory = ViewModelFactory.getInstance()
        val dataViewModel = ViewModelProvider(
            this,
            factory
        )[MoviesViewModel::class.java]

        val extras = intent.extras
        if(extras != null){
            val getExtra = intent.getParcelableExtra<DataClass>(EXTRA_DATA)
            val id = getExtra?.id
            if (id != null) {
                dataViewModel.getMoviesById(id)
            }
            with(binding){
                dataViewModel.movies.observe(this@DetailMovieActivity, { value ->
                    Glide.with(applicationContext).load(value[0].image).apply(
                        RequestOptions.placeholderOf(R.drawable.ic_animated_loading)
                            .error(R.drawable.ic_broken)
                    ).into(coverImage)
                    tvCategoryMovie.text = value[0].rating
                    tvDirector.text = value[0].directors
                    tvGenre.text = value[0].genre
                    tvTitle.text = value[0].title
                    tvYear.text = value[0].releaseYear.toString()
                    synopsis.text = value[0].description
                })

                dataViewModel.isLoading.observe(this@DetailMovieActivity, {value ->
                    if(value)
                        progressBarDetailMovies.visibility = View.VISIBLE
                    else
                        progressBarDetailMovies.visibility = View.GONE
                })


            }
        }

    }
}