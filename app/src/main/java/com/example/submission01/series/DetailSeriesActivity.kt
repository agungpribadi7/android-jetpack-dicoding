package com.example.submission01.series

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission01.R
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.databinding.ActivityDetailSeriesBinding
import com.example.submission01.vo.Status
import org.koin.android.viewmodel.ext.android.viewModel

class DetailSeriesActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailSeriesBinding
    private val viewModel : SeriesViewModel by viewModel()
    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Submission 3"


        val extras = intent.extras
        if(extras != null){
            val getExtra = intent.getParcelableExtra<DataEntity>(EXTRA_DATA)
            val id = getExtra?.id
            if(id != null) {
                with(binding) {
                    starSeries.setOnClickListener{
                        if(starSeries.isChecked) {
                            val result = viewModel.addFavorite(id)
                            if(result.status == Status.SUCCESS) {
                                Toast.makeText(this@DetailSeriesActivity, "Added to favorites", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@DetailSeriesActivity, "Error!", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            val result = viewModel.deleteFavorite(id)
                            if(result.status == Status.SUCCESS) {
                                Toast.makeText(this@DetailSeriesActivity, "Removed to favorites", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@DetailSeriesActivity, "Error!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    viewModel.getDataById(id).observe(this@DetailSeriesActivity, { value ->
                        if(value != null) {
                            when(value.status) {
                                Status.LOADING -> progressBarDetailSeries.visibility = View.VISIBLE
                                Status.SUCCESS -> {
                                    starSeries.isChecked = value.data?.get(0)?.favorite == true
                                    progressBarDetailSeries.visibility = View.GONE
                                    Glide.with(applicationContext).load(value.data?.get(0)?.image).apply(
                                        RequestOptions.placeholderOf(R.drawable.ic_animated_loading)
                                            .error(R.drawable.ic_broken)
                                    ).into(coverImage)
                                    tvCategory.text = value.data?.get(0)?.rating
                                    tvDirector.text = value.data?.get(0)?.directors
                                    tvGenre.text = value.data?.get(0)?.genre
                                    tvTitle.text = value.data?.get(0)?.title
                                    tvYear.text = value.data?.get(0)?.releaseYear.toString()
                                    synopsis.text = value.data?.get(0)?.description
                                }
                                Status.ERROR -> {
                                    binding.progressBarDetailSeries.visibility = View.GONE
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