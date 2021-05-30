package com.example.submission01.movies

import com.example.submission01.data.source.ItemRepository
import com.example.submission01.data.source.local.Data
import com.example.submission01.data.source.local.entity.DataEntity
import org.junit.Before
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.example.submission01.utils.SortUtils
import com.example.submission01.vo.Resource
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
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MoviesViewModelTest {
    private lateinit var viewModel : MoviesViewModel
    private lateinit var data : DataEntity
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository : ItemRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<DataEntity>>>

    @Mock
    private lateinit var observerSort : Observer<Resource<List<DataEntity>>>

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(repository)
        data = Data.getMovies()[0]
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getMoviesViewModel should be success`() = runBlocking {

        val dummyMovies = MutableLiveData<Resource<PagedList<DataEntity>>>()
        dummyMovies.value = Resource.success(PagedTestDataSources.snapshot(Data.getMovies()))


        `when`(repository.getNMovies(10)).thenReturn(dummyMovies)
        viewModel.getMoviesViewModel(10).observeForever(observer)
        observer.onChanged(dummyMovies.value)
        verify(repository).getNMovies(10)

        val dataEntities = viewModel.getMoviesViewModel(10).value?.data

        assertEquals(dummyMovies.value?.data, dataEntities)
        assertEquals(10, dummyMovies.value?.data?.size)
    }

    @Test
    fun `getMoviesViewModel should be success but data is empty`() = runBlocking {

        val dummyMovies = MutableLiveData<Resource<PagedList<DataEntity>>>()
        dummyMovies.value = Resource.success(PagedTestDataSources.snapshot())


        `when`(repository.getNMovies(10)).thenReturn(dummyMovies)
        viewModel.getMoviesViewModel(10).observeForever(observer)
        observer.onChanged(dummyMovies.value)
        verify(repository).getNMovies(10)

        val dataEntitiesSize = viewModel.getMoviesViewModel(10).value?.data?.size

        assertTrue("size of data should be 0, actual is $dataEntitiesSize", dataEntitiesSize == 0)
    }

    @Test
    fun `add favorite should be success`() = runBlocking {
        val dataDummy = Data.getMovies()[0]
        val favDummy = Resource.success("Success")
        `when`(repository.addFavoriteMovie(dataDummy.id)).thenReturn(favDummy)
        viewModel.addFavorite(dataDummy.id)
        verify(repository).addFavoriteMovie(dataDummy.id)

        val actualReturn = viewModel.addFavorite(dataDummy.id)
        assertNotNull(actualReturn)
        assertEquals(actualReturn.data, "Success")
        assertEquals(actualReturn, favDummy)
    }

    @Test
    fun `delete favorite should be success`() = runBlocking {
        val dataDummy = Data.getMovies()[0]
        val favDummy = Resource.success("Success")
        `when`(repository.deleteFavoriteMovie(dataDummy.id)).thenReturn(favDummy)
        viewModel.deleteFavorite(dataDummy.id)
        verify(repository).deleteFavoriteMovie(dataDummy.id)

        val actualReturn = viewModel.deleteFavorite(dataDummy.id)
        assertNotNull(actualReturn)
        assertEquals(actualReturn.data, "Success")
        assertEquals(actualReturn, favDummy)
    }

    @Test
    fun `sorting favorite should be success`() = runBlocking {
        val dataDummy = Data.getMovies()[0]
        val favDummy = Resource.success("Success")
        `when`(repository.addFavoriteMovie(dataDummy.id)).thenReturn(favDummy)
        viewModel.addFavorite(dataDummy.id)
        verify(repository).addFavoriteMovie(dataDummy.id)

        val fakeFav = MutableLiveData<Resource<List<DataEntity>>>()
        val fakeList = listOf(dataDummy)
        fakeFav.value = Resource.success(fakeList)

        `when`(repository.getFavoriteMovies(SortUtils.ID)).thenReturn(fakeFav)
        viewModel.sort.value = SortUtils.ID
        viewModel.getFavorites.observeForever(observerSort)
        observerSort.onChanged(fakeFav.value)
        verify(repository).getFavoriteMovies(SortUtils.ID)

        val listFav = repository.getFavoriteMovies(SortUtils.ID)
        assertNotNull(listFav)
        assertEquals(listFav.value?.data?.size, 1)
        assertEquals(listFav.value?.data?.get(0)?.id, dataDummy.id)
    }



    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    class PagedTestDataSources private constructor(private val items: List<DataEntity>) : PositionalDataSource<DataEntity>() {
        companion object {
            fun snapshot(items: List<DataEntity> = listOf()): PagedList<DataEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 4)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<DataEntity>) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<DataEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}