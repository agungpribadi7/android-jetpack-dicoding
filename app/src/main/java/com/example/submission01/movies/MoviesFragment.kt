package com.example.submission01.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.submission01.data.DataClass
import com.example.submission01.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment(), MoviesAdapter.MoviesClickListener {
    private lateinit var binding : FragmentMoviesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null){
            val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MoviesViewModel::class.java]
            val movies = viewModel.getData()
            val moviesAdapter = MoviesAdapter(this)
            moviesAdapter.setData(movies)


            with(binding.recycleViewMovies) {
                layoutManager = GridLayoutManager(context, 2)
                adapter = moviesAdapter
            }
        }
    }

    override fun onClick(data : DataClass) {
        val intent = Intent(this.context,  DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_DATA, data)
        startActivity(intent)
    }


}