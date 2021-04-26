package com.example.submission01.series

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.submission01.data.DataClass
import com.example.submission01.databinding.FragmentSeriesBinding


class SeriesFragment : Fragment(), SeriesAdapter.SeriesClickListener {
    private lateinit var binding : FragmentSeriesBinding


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
            val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SeriesViewModel::class.java]
            val series = viewModel.getData()
            val seriesAdapter = SeriesAdapter(this)
            seriesAdapter.setData(series)


            with(binding.recycleViewSeries) {
                layoutManager = GridLayoutManager(context, 2)
                adapter = seriesAdapter
            }
        }
    }

    override fun onClick(data : DataClass) {
        val intent = Intent(this.context,  DetailSeriesActivity::class.java)
        intent.putExtra(DetailSeriesActivity.EXTRA_DATA, data)
        startActivity(intent)
    }
}