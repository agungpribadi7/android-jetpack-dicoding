package com.example.submission01.series

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.databinding.FragmentSeriesBinding
import com.example.submission01.vo.Status
import org.koin.android.viewmodel.ext.android.viewModel

class SeriesFragment : Fragment(), SeriesAdapter.SeriesClickListener {
    private lateinit var binding : FragmentSeriesBinding
    private val viewModel : SeriesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeriesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null){
            val seriesAdapter = SeriesAdapter(this)

            viewModel.getData().observe(viewLifecycleOwner, {listSeries ->
                    when (listSeries.status) {
                        Status.LOADING -> binding.progressBarSeries.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding.progressBarSeries.visibility = View.GONE
                            listSeries.data?.let { seriesAdapter.submitList(it) }
                            with(binding.recycleViewSeries) {
                                layoutManager = GridLayoutManager(context, 2)
                                adapter = seriesAdapter
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBarSeries.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }

            })

        }
    }

    override fun onClick(data : DataEntity) {
        val intent = Intent(this.context,  DetailSeriesActivity::class.java)
        intent.putExtra(DetailSeriesActivity.EXTRA_DATA, data)
        startActivity(intent)
    }
}