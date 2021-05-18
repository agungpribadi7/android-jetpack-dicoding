package com.example.submission01.movies

import com.example.submission01.data.source.ItemRepository
import com.example.submission01.data.source.local.Data
import com.example.submission01.data.source.local.DataClass
import org.junit.Before
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MoviesViewModelTest {
    private lateinit var viewModel : MoviesViewModel
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
        viewModel = MoviesViewModel(repository)
        data = Data.getMovies()[0]
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getData() = runBlocking {

        val dummyMovies = Data.getMovies()
        val movies = MutableLiveData<ArrayList<DataClass>>()
        movies.value = dummyMovies
        `when`(repository.getNMovies(10)).thenReturn(movies.value)
        viewModel.getMovies()
        assertNotNull(viewModel.movies.value)
        assertEquals(10, viewModel.movies.value?.size)

        viewModel.movies.observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getDataById() = runBlocking {
        val singleMovie = MutableLiveData<ArrayList<DataClass>>()
        val arrData = ArrayList<DataClass>()
        arrData.add(data)
        singleMovie.value = arrData
        val id : String = singleMovie.value?.get(0)?.id.toString()
        `when`(repository.getNMovies(1, id.toInt())).thenReturn(singleMovie.value)
        data.id?.let { viewModel.getMoviesById(it) }
        assertNotNull(viewModel.movies.value)
        assertNotNull(data)
        assertEquals(1, viewModel.movies.value?.size)
        assertEquals(data.image, viewModel.movies.value?.get(0)?.image)
        assertEquals(data.title, viewModel.movies.value?.get(0)?.title)
        assertEquals(data.directors, viewModel.movies.value?.get(0)?.directors)
        assertEquals(data.rating, viewModel.movies.value?.get(0)?.rating)
        assertEquals(data.genre, viewModel.movies.value?.get(0)?.genre)
        assertEquals(data.releaseYear, viewModel.movies.value?.get(0)?.releaseYear)
        assertEquals(data.description, viewModel.movies.value?.get(0)?.description)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}