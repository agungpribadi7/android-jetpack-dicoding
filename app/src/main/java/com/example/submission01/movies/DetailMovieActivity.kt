package com.example.submission01.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission01.R
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.databinding.ActivityDetailMovieBinding
import com.example.submission01.vo.Status
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailMovieBinding
    private val dataViewModel :MoviesViewModel by viewModel()
    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Submission 3"

        val extras = intent.extras
        if(extras != null){
            val getExtra = intent.getParcelableExtra<DataEntity>(EXTRA_DATA)
            val id = getExtra?.id
            with(binding){
                if (id != null) {
                    starMovies.setOnClickListener{
                        if(starMovies.isChecked) {
                            val result = dataViewModel.addFavorite(id)
                            if(result.status == Status.SUCCESS) {
                                Toast.makeText(this@DetailMovieActivity, "Added to favorites", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@DetailMovieActivity, "Error!", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            val result = dataViewModel.deleteFavorite(id)
                            if(result.status == Status.SUCCESS) {
                                Toast.makeText(this@DetailMovieActivity, "Removed from favorites", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@DetailMovieActivity, "Error!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    dataViewModel.getMoviesById(id).observe(this@DetailMovieActivity, { value ->
                        if(value != null) {
                            when(value.status) {
                                Status.LOADING -> progressBarDetailMovies.visibility = View.VISIBLE
                                Status.SUCCESS -> {
                                    progressBarDetailMovies.visibility = View.GONE
                                    starMovies.isChecked = value.data?.get(0)?.favorite == true
                                    Glide.with(applicationContext).load(value.data?.get(0)?.image).apply(
                                        RequestOptions.placeholderOf(R.drawable.ic_animated_loading)
                                            .error(R.drawable.ic_broken)
                                    ).into(coverImage)
                                    tvCategoryMovie.text = value.data?.get(0)?.rating
                                    tvDirector.text = value.data?.get(0)?.directors
                                    tvGenre.text = value.data?.get(0)?.genre
                                    tvTitle.text = value.data?.get(0)?.title
                                    tvYear.text = value.data?.get(0)?.releaseYear.toString()
                                    synopsis.text = value.data?.get(0)?.description
                                }
                                Status.ERROR -> {
                                    binding.progressBarDetailMovies.visibility = View.GONE
                                    Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }

                    })
                }
            }
        }

    }
}