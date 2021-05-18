package com.example.submission01.movies

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.submission01.data.source.local.DataClass
import com.example.submission01.databinding.FragmentMoviesBinding
import com.example.submission01.viewmodel.ViewModelFactory

class MoviesFragment : Fragment(), MoviesAdapter.MoviesClickListener {
    private lateinit var binding : FragmentMoviesBinding
    private lateinit var viewModel : MoviesViewModel

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
            val factory = ViewModelFactory.getInstance()
            activity?.let {
                viewModel = ViewModelProvider(
                    it,
                    factory
                )[MoviesViewModel::class.java]
            }
            viewModel.getMovies()
            val moviesAdapter = MoviesAdapter(this)

            viewModel.isLoading.observe(viewLifecycleOwner, {value ->
                if(value)
                    binding.progressBarMovies.visibility = View.VISIBLE
                else
                    binding.progressBarMovies.visibility = View.GONE
            })

            viewModel.movies.observe(viewLifecycleOwner, { listMoviesMap ->
                moviesAdapter.setData(listMoviesMap)
                with(binding.recycleViewMovies) {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = moviesAdapter
                }
            })



        }
    }

    override fun onClick(data : DataClass) {
        val intent = Intent(this.context,  DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_DATA, data)
        startActivity(intent)
    }


}