package com.example.submission01.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.databinding.FragmentMoviesBinding
import com.example.submission01.vo.Status
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment(), MoviesAdapter.MoviesClickListener {
    private lateinit var binding : FragmentMoviesBinding
    private val viewModel : MoviesViewModel by viewModel()

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

            val moviesAdapter = MoviesAdapter(this)

            viewModel.getMoviesViewModel().observe(viewLifecycleOwner, { listMoviesMap ->
                    when(listMoviesMap.status) {
                        Status.LOADING -> binding.progressBarMovies.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding.progressBarMovies.visibility = View.GONE
                            listMoviesMap.data?.let { moviesAdapter.submitList(it) }
                            with(binding.recycleViewMovies) {
                                layoutManager = GridLayoutManager(context, 2)
                                adapter = moviesAdapter
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBarMovies.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }

            })



        }
    }

    override fun onClick(data : DataEntity) {
        val intent = Intent(this.context,  DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_DATA, data)
        startActivity(intent)
    }


}