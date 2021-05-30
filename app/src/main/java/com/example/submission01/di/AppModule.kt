package com.example.submission01.di

import com.example.submission01.movies.MoviesViewModel
import com.example.submission01.series.SeriesViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { SeriesViewModel(get()) }
}