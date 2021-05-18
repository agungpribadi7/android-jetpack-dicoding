package com.example.submission01.series

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.submission01.data.source.local.DataClass
import com.example.submission01.databinding.FragmentSeriesBinding
import com.example.submission01.viewmodel.ViewModelFactory


class SeriesFragment : Fragment(), SeriesAdapter.SeriesClickListener {
    private lateinit var binding : FragmentSeriesBinding
    private lateinit var viewModel : SeriesViewModel

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
            val factory = ViewModelFactory.getInstance()
            val seriesAdapter = SeriesAdapter(this)
            activity?.let {
                viewModel = ViewModelProvider(it, factory)[SeriesViewModel::class.java]
            }
            viewModel.getData()
            viewModel.isLoading.observe(viewLifecycleOwner, { value ->
                if(value)
                    binding.progressBarSeries.visibility = View.VISIBLE
                else
                    binding.progressBarSeries.visibility = View.GONE
            })
            viewModel.series.observe(viewLifecycleOwner, {listSeries ->
                seriesAdapter.setData(listSeries)

                with(binding.recycleViewSeries) {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = seriesAdapter
                }
            })

        }
    }

    override fun onClick(data : DataClass) {
        val intent = Intent(this.context,  DetailSeriesActivity::class.java)
        intent.putExtra(DetailSeriesActivity.EXTRA_DATA, data)
        startActivity(intent)
    }
}