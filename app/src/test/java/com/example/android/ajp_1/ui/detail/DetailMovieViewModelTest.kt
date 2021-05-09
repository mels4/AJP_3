package com.example.android.ajp_1.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.android.ajp_1.entity.remote.Repository
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.entity.remote.response.TvShowResult
import com.example.android.ajp_1.utils.FakeDataDummy
import com.example.android.ajp_1.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`

@RunWith(org.mockito.junit.MockitoJUnitRunner::class)
class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel
    private val dummyDataMovie = FakeDataDummy.generateDummyMovie()[0]
    private var dummyDataTv = FakeDataDummy.generateDummyTv()[0]
    private val movieId = dummyDataMovie.movieId
    private val tvId = dummyDataTv.tvShowId

    @get:Rule
    var insTExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<Resource<MovieResponse>>

    @Mock
    private lateinit var observertv: Observer<Resource<TvShowResult>>


    @Before
    fun setUp(){
        viewModel= DetailMovieViewModel(repository)
        viewModel.setSelectedMovie(movieId)
        viewModel.setSelectedMovie(tvId)
    }

    @Test
    fun getDetailMovie() {
        val dummyMovie = Resource.success(dummyDataMovie)
        val data = MutableLiveData<Resource<MovieResponse>>()
        data.value = dummyMovie

        `when`(repository.getDetailMovie(movieId)).thenReturn(data)

        viewModel.getDetailMovie.observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun DetailTv(){
        val dummyTv = Resource.success(dummyDataTv)
        val data = MutableLiveData<Resource<TvShowResult>>()
        data.value = dummyTv

        `when`(repository.getDetailTv(tvId)).thenReturn(data)

        viewModel.getDetailTvShow.observeForever(observertv)
        verify(observertv).onChanged(dummyTv)


    }
}