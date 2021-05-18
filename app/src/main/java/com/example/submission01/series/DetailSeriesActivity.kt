package com.example.submission01.series

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission01.R
import com.example.submission01.data.source.local.DataClass
import com.example.submission01.databinding.ActivityDetailSeriesBinding
import com.example.submission01.viewmodel.ViewModelFactory

class DetailSeriesActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailSeriesBinding
    private lateinit var viewModel : SeriesViewModel
    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Submission 2"
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[SeriesViewModel::class.java]

        val extras = intent.extras
        if(extras != null){
            val getExtra = intent.getParcelableExtra<DataClass>(EXTRA_DATA)
            val id = getExtra?.id
            if(id != null) {
                viewModel.getDataById(id)
                with(binding) {
                    viewModel.series.observe(this@DetailSeriesActivity, { value ->

                        Glide.with(applicationContext).load(value?.get(0)?.image).apply(
                            RequestOptions.placeholderOf(R.drawable.ic_animated_loading)
                                .error(R.drawable.ic_broken)
                        ).into(coverImage)
                        tvCategory.text = value?.get(0)?.rating
                        tvDirector.text = value?.get(0)?.directors
                        tvGenre.text = value?.get(0)?.genre
                        tvTitle.text = value?.get(0)?.title
                        tvYear.text = value?.get(0)?.releaseYear.toString()
                        synopsis.text = value?.get(0)?.description
                    })
                    viewModel.isLoading.observe(this@DetailSeriesActivity, { value ->
                        if(value)
                            progressBarDetailSeries.visibility = View.VISIBLE
                        else
                            progressBarDetailSeries.visibility = View.GONE
                    })
                }

            }

        }
    }
}