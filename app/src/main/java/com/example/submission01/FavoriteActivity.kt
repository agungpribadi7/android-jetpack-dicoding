package com.example.submission01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submission01.databinding.ActivityFavoriteBinding
import com.example.submission01.databinding.ActivityMainBinding

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding : ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorites"

        val sectionsPagerAdapter = SectionsPagerAdapterFavorite(this, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }
}