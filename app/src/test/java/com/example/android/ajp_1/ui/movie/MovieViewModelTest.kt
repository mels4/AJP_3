package com.example.android.ajp_1.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.android.ajp_1.entity.remote.Repository
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.utils.FakeDataDummy
import com.example.android.ajp_1.vo.Resource
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieResponse>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieResponse>


    @Before
    fun setUp(){
        viewModel = spy(MovieViewModel(repository))
    }

    @Test
    fun getMovie() {
        val fakeDummy = Resource.success(pagedList)
        val query = ""
        `when`(fakeDummy.data?.size).thenReturn(1)
        val movie = MutableLiveData<Resource<PagedList<MovieResponse>>>()
        movie.value = fakeDummy

        `when`(repository.getMovieTop(query)).thenReturn(movie)
        val entity = viewModel.getMoviePopular(query).value?.data
        verify(repository).getMovieTop(query)
        assertNotNull(entity)
        assertEquals(1, entity?.size)

        viewModel.getMoviePopular(query).observeForever(observer)
        verify(observer).onChanged(fakeDummy)
    }
}