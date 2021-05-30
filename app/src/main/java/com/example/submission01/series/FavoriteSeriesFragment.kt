package com.example.submission01.series

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission01.R
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.databinding.FragmentFavoriteSeriesBinding
import com.example.submission01.utils.SortUtils
import com.example.submission01.vo.Status
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteSeriesFragment : Fragment(), AdapterView.OnItemSelectedListener, FavoriteSeriesAdapter.SeriesClickListener {

    private lateinit var binding : FragmentFavoriteSeriesBinding
    private val options = listOf("Id", "Title")
    private val seriesViewModel : SeriesViewModel by viewModel()
    private var defaultSort = SortUtils.ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteSeriesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity != null && context != null) {
            val spin = binding.spinnerSeries
            val adapter: ArrayAdapter<String>? =
                activity?.applicationContext?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_item, options) }
            adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spin.adapter = adapter
            spin.onItemSelectedListener = this
            val seriesAdapter = FavoriteSeriesAdapter(this)


            with(binding) {
                seriesViewModel.sort.value = defaultSort
                seriesViewModel.getFavorite.observe(viewLifecycleOwner, {listSeries ->
                    if(listSeries != null) {
                        when (listSeries.status) {
                            Status.LOADING -> progressBarFavSeries.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                binding.progressBarFavSeries.visibility = View.GONE
                                listSeries.data?.let { seriesAdapter.setData(listSeries.data.toMutableList()) }
                                val detailTxt =
                                    "Showing " + listSeries.data?.size.toString() + " Items"
                                descriptionSeriesFav.text = detailTxt
                                with(binding.recycleViewFavSeries) {
                                    layoutManager = LinearLayoutManager(context)
                                    recycleViewFavSeries.adapter = seriesAdapter
                                }
                                if (listSeries.data?.size == 0) {
                                    Glide.with(this@FavoriteSeriesFragment)
                                        .load("https://i.ibb.co/FkpWQ4w/nodataflutterprojek.png")
                                        .into(binding.noDataSeries)
                                }

                            }
                            Status.ERROR -> {
                                binding.progressBarFavSeries.visibility = View.GONE
                                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                })
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        defaultSort = if(position == 0) {
            SortUtils.ID
        } else {
            SortUtils.TITLE
        }
        seriesViewModel.sort.value = defaultSort
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onClick(data: DataEntity) {
        val intent = Intent(this.context,  DetailSeriesActivity::class.java)
        intent.putExtra(DetailSeriesActivity.EXTRA_DATA, data)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        seriesViewModel.sort.value = defaultSort

    }


}