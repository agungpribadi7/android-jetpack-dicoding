package com.example.submission01.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submission01.FakeItemRepository
import com.example.submission01.data.source.local.Data
import com.example.submission01.data.source.local.LocalDataSource
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.data.source.remote.RemoteDataSource
import com.example.submission01.utils.AppExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import androidx.paging.DataSource
import com.example.submission01.utils.PagedListUtil
import com.example.submission01.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ItemRepositoryTest {
    private val remote = mock(RemoteDataSource::class.java)

    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val itemRepository = FakeItemRepository(remote, local, appExecutors)
    private val movieResponse = Data.generateDataMovieDummyResponse()
    private val seriesResponse = Data.generateDataSeriesDummyResponse()
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    private val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, DataEntity>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getNMovies() = runBlocking {
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        itemRepository.getNMovies(2)
        verify(local).getAllMovies()

        val tempDummyMovies = ArrayList<DataEntity>()
        tempDummyMovies.add(Data.getMovies()[0])
        tempDummyMovies.add(Data.getMovies()[1])

        val dummyMovies = Resource.success(PagedListUtil.mockPagedList(tempDummyMovies))

        assertNotNull(dummyMovies.data)
        assertEquals(movieResponse.size.toLong(), dummyMovies.data?.size?.toLong())
        assertEquals(movieResponse[0].id, dummyMovies.data?.get(0)?.id)
    }

    @Test
    fun getNTv() = runBlocking {
        `when`(local.getAllSeries()).thenReturn(dataSourceFactory)
        itemRepository.getNTv(2)

        val tempDummySeries = ArrayList<DataEntity>()
        tempDummySeries.add(Data.getTvShows()[0])
        tempDummySeries.add(Data.getTvShows()[1])

        val dummySeries = Resource.success(PagedListUtil.mockPagedList(tempDummySeries))
        verify(local).getAllSeries()
        assertNotNull(dummySeries)
        assertEquals(seriesResponse.size.toLong(), dummySeries.data?.size?.toLong())
        assertEquals(seriesResponse[0].id, dummySeries.data?.get(0)?.id)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}