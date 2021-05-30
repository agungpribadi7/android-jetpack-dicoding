package com.example.submission01.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.submission01.databinding.FragmentFavoriteMoviesBinding
import org.koin.android.ext.android.bind
import android.widget.AdapterView;
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission01.R
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.utils.SortUtils
import com.example.submission01.vo.Status
import org.koin.android.viewmodel.ext.android.viewModel


class FavoriteMoviesFragment : Fragment(), AdapterView.OnItemSelectedListener, FavoriteMoviesAdapter.MoviesClickListener {

    private val options = listOf("Id", "Title")
    private lateinit var binding : FragmentFavoriteMoviesBinding
    private val dataViewModel :MoviesViewModel by viewModel()
    private var defaultSort = SortUtils.ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteMoviesBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null && context != null) {
            val spin = binding.spinnerMovies
            val adapter: ArrayAdapter<String>? =
                activity?.applicationContext?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_item, options) }
            adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spin.adapter = adapter
            spin.onItemSelectedListener = this
            val moviesAdapter = FavoriteMoviesAdapter(this)
            dataViewModel.sort.value = defaultSort

            dataViewModel.getFavorites.observe(viewLifecycleOwner, { listMovies ->
                if(listMovies != null) {
                    with(binding) {
                        when (listMovies.status) {
                            Status.LOADING -> binding.progressBarFavMovies.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                binding.progressBarFavMovies.visibility = View.GONE
                                listMovies.data?.let { moviesAdapter.setData(listMovies.data.toMutableList()) }
                                val detailTxt =
                                    "Showing " + listMovies.data?.size.toString() + " Items"
                                descriptionMoviesFav.text = detailTxt
                                with(binding.recycleViewFavMovies) {
                                    layoutManager = LinearLayoutManager(context)
                                    recycleViewFavMovies.adapter = moviesAdapter
                                }
                                if (listMovies.data?.size == 0) {
                                    Glide.with(this@FavoriteMoviesFragment)
                                        .load("https://i.ibb.co/FkpWQ4w/nodataflutterprojek.png")
                                        .into(binding.noDataMovies)
                                }

                            }
                            Status.ERROR -> {
                                binding.progressBarFavMovies.visibility = View.GONE
                                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            })
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        defaultSort = if(position == 0) {
            SortUtils.ID
        } else {
            SortUtils.TITLE
        }
        dataViewModel.sort.value = defaultSort
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onClick(data: DataEntity) {
        val intent = Intent(this.context,  DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_DATA, data)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        dataViewModel.sort.value = defaultSort
    }

}