package com.example.android.ajp_1.entity.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.android.ajp_1.api.ApiResponse
import com.example.android.ajp_1.entity.local.LocalDataSource
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.entity.remote.response.TvShowResult
import com.example.android.ajp_1.utils.AppExecutors
import com.example.android.ajp_1.utils.FakeDataDummy
import com.example.android.ajp_1.utils.LiveDataUtils
import com.example.android.ajp_1.utils.PagedListUtil
import com.example.android.ajp_1.vo.Resource
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class RepositoryTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository = mock(FakeRepository::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val movieResponse = FakeDataDummy.generateDummyMovie()
    private val movieId = movieResponse[0].movieId
    private val tvResponse = FakeDataDummy.generateDummyTv()
    private val tvId = tvResponse[0].tvShowId
    private val query: String = "the"


    @Test
    fun getMovie(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieResponse>
        `when`(local.getMovieRoom(query)).thenReturn(dataSourceFactory)
        repository.getMovieTop(query)

        val movieEntity = Resource.success(PagedListUtil.mockPagedList(FakeDataDummy.generateDummyMovie()))
        verify(repository).getMovieTop(query)
        assertNotNull(movieEntity.data)
        assertEquals(movieResponse.size.toLong(), movieEntity.data?.size?.toLong())

    }

    @Test
    fun getTv(){
        val dataSource = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowResult>
        `when`(local.getTvRoom()).thenReturn(dataSource)
        repository.getTvShowTop()

        val tvEntity = Resource.success(PagedListUtil.mockPagedList(FakeDataDummy.generateDummyTv()))
        verify(repository).getTvShowTop()
        assertNotNull(tvEntity.data)
        assertEquals(tvResponse.size.toLong(), tvEntity.data?.size?.toLong())

    }

    @Test
    fun getDetailMovie(){
        val detailMovie = MutableLiveData<MovieResponse>()
        detailMovie.value = FakeDataDummy.generateDummyMovie()[0]

        `when`(local.getMovieById(movieId)).thenReturn(detailMovie)

        val result = LiveDataUtils.getValue(local.getMovieById(movieId))
        verify(local).getMovieById(movieId)

        assertNotNull(result)
        assertNotNull(result.movieId)
        assertEquals(movieResponse[0].title, result.title)

    }

    @Test
    fun getDetailTv(){
        val detailTv = MutableLiveData<TvShowResult>()
        detailTv.value = FakeDataDummy.generateDummyTv()[0]

        `when`(local.getTvByIDRoom(tvId)).thenReturn(detailTv)

        val result = LiveDataUtils.getValue(local.getTvByIDRoom(tvId))
        verify(local).getTvByIDRoom(tvId)

        assertNotNull(result)
        assertNotNull(result.title)
        assertEquals(tvResponse[0].title, result.title)
    }
}