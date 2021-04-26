package com.example.submission01.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission01.R
import com.example.submission01.data.DataClass
import com.example.submission01.databinding.ActivityDetailMovieBinding

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailMovieBinding
    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dataViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MoviesViewModel::class.java]

        val extras = intent.extras
        if(extras != null){
            val getExtra = intent.getParcelableExtra<DataClass>(EXTRA_DATA)
            dataViewModel.setIdData(getExtra?.id.toString())
            val data = dataViewModel.getDataById()
            with(binding){
                Glide.with(applicationContext).load(data?.image).apply(
                    RequestOptions.placeholderOf(R.drawable.ic_animated_loading)
                        .error(R.drawable.ic_broken)
                ).into(coverImage)
                tvCategory.text = data?.rating
                tvDirector.text = data?.directors
                tvGenre.text = data?.genre
                tvTitle.text = data?.title
                tvYear.text = data?.releaseYear.toString()
                synopsis.text = data?.description
            }
        }

    }
}