package com.example.submission01.series

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.submission01.data.source.ItemRepository
import com.example.submission01.data.source.local.Data
import com.example.submission01.data.source.local.DataClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class SeriesViewModelTest {
    private lateinit var viewModel : SeriesViewModel
    private lateinit var data : DataClass
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository : ItemRepository

    @Mock
    private lateinit var observer: Observer<ArrayList<DataClass>>

    @Before
    fun setUp() {
        viewModel = SeriesViewModel(repository)
        data = Data.getTvShows()[0]
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getData() = runBlocking {
        val listSeries = Data.getTvShows()
        `when`(repository.getNTv(10)).thenReturn(listSeries)
        viewModel.getData()

        assertNotNull(viewModel.series.value)
        assertEquals(10, viewModel.series.value?.size)

        viewModel.series.observeForever(observer)
        verify(observer).onChanged(listSeries)
    }

    @Test
    fun getDataById() = runBlocking {
        val singleSeries = MutableLiveData<ArrayList<DataClass>>()
        val arrData = ArrayList<DataClass>()
        arrData.add(data)
        val id = arrData[0].id.toString()
        singleSeries.value = arrData
        `when`(repository.getNTv(1, id.toInt())).thenReturn(singleSeries.value)
        viewModel.getDataById(id.toInt())
        assertNotNull(data)
        assertNotNull(viewModel.series.value)
        assertEquals(data.image, viewModel.series.value?.get(0)?.image)
        assertEquals(data.title, viewModel.series.value?.get(0)?.title)
        assertEquals(data.directors, viewModel.series.value?.get(0)?.directors)
        assertEquals(data.releaseYear, viewModel.series.value?.get(0)?.releaseYear)
        assertEquals(data.rating, viewModel.series.value?.get(0)?.rating)
        assertEquals(data.genre, viewModel.series.value?.get(0)?.genre)
        assertEquals(data.description, viewModel.series.value?.get(0)?.description)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}