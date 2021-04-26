package com.example.submission01.series

import com.example.submission01.data.Data
import com.example.submission01.data.DataClass
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class SeriesViewModelTest {
    lateinit var viewModel : SeriesViewModel
    lateinit var data : DataClass
    @Before
    fun setUp(){
        viewModel = SeriesViewModel()
        viewModel.setIdData("1")
        data = Data.getTvShows()[0]
    }

    @Test
    fun getData() {
        val dataCollection = viewModel.getData()
        assertEquals(10, dataCollection.size)
        for (i in 0 .. 9){
            assertNotNull(dataCollection[i])
        }
    }

    @Test
    fun getDataById() {
        val viewData = viewModel.getDataById()
        assertNotNull(data)
        assertNotNull(viewData)
        assertEquals(data.image, viewData?.image)
        assertEquals(data.title, viewData?.title)
        assertEquals(data.directors, viewData?.directors)
        assertEquals(data.releaseYear, viewData?.releaseYear)
        assertEquals(data.rating, viewData?.rating)
        assertEquals(data.genre, viewData?.genre)
        assertEquals(data.description, viewData?.description)
    }
}