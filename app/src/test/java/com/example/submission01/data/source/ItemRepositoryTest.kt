package com.example.submission01.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submission01.FakeItemRepository
import com.example.submission01.data.source.local.Data
import com.example.submission01.data.source.local.DataClass
import com.example.submission01.data.source.remote.RemoteDataSource
import com.nhaarman.mockitokotlin2.any
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
import com.nhaarman.mockitokotlin2.doAnswer

class ItemRepositoryTest {
    private val remote = mock(RemoteDataSource::class.java)
    private val itemRepository = FakeItemRepository(remote)

    private val movieResponse = Data.generateDataMovieDummyResponse()[0]
    private val seriesResponse = Data.generateDataSeriesDummyResponse()[0]
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getNMovies() = runBlocking {
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[2] as RemoteDataSource.LoadMovieApi).responsesRetrieved(
                    movieResponse
                )
                null
            }.`when`(remote).getNMovies(eq(1), eq(120),any())
        }

        val dataResponse: ArrayList<DataClass> = itemRepository.getNMovies(1, 120)


        runBlocking {
            verify(remote).getNMovies(any(), any(), any())
        }
        assertNotNull(dataResponse)
        assertEquals(movieResponse.id, dataResponse[0].id)
    }

    @Test
    fun getNTv() = runBlocking {
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[2] as RemoteDataSource.LoadSeriesApi).responsesRetrived(
                    seriesResponse
                )
                null
            }.`when`(remote).getNSeries(eq(1), eq(121),any())
        }

        val dataResponse: ArrayList<DataClass> = itemRepository.getNTv(1, 121)


        runBlocking {
            verify(remote).getNSeries(any(), any(), any())
        }
        assertNotNull(dataResponse)
        assertEquals(seriesResponse.id, dataResponse[0].id)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}