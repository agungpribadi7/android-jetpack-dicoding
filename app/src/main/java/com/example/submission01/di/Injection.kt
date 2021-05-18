package com.example.submission01.di

import com.example.submission01.data.source.ItemRepository
import com.example.submission01.data.source.remote.RemoteDataSource

object Injection {

    fun provideItemRepository(): ItemRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return ItemRepository.getInstance(remoteDataSource)
    }
}