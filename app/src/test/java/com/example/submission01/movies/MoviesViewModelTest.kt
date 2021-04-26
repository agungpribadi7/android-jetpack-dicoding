package com.example.submission01.movies

import com.example.submission01.data.Data
import com.example.submission01.data.DataClass
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class MoviesViewModelTest {
    lateinit var viewModel : MoviesViewModel
    lateinit var data : DataClass
    @Before
    fun setUp() {
        viewModel = MoviesViewModel()
        viewModel.setIdData("1")
        data = Data.getMovies()[0]
    }

    @Test
    fun getData() {
        val collectionData = viewModel.getData()
        assertEquals(10, collectionData.size)
        for(i in 0 .. 9){
            assertNotNull(collectionData[i])
        }
    }

    @Test
    fun getDataById() {
        val viewData = viewModel.getDataById()
        assertNotNull(viewData)
        assertNotNull(data)
        assertEquals(data.image, viewData?.image)
        assertEquals(data.title, viewData?.title)
        assertEquals(data.directors, viewData?.directors)
        assertEquals(data.rating, viewData?.rating)
        assertEquals(data.genre, viewData?.genre)
        assertEquals(data.releaseYear, viewData?.releaseYear)
        assertEquals(data.description, viewData?.description)
    }
}