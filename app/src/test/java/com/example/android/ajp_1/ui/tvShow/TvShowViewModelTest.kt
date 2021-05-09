package com.example.android.ajp_1.ui.tvShow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.android.ajp_1.entity.remote.Repository
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

@RunWith(org.mockito.runners.MockitoJUnitRunner::class)
class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskRule= InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowResult>>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowResult>

    @Before
    fun setUp(){
        viewModel = TvShowViewModel(repository)
    }

    @Test
    fun getTvShow() {
        val fakeDummy = Resource.success(pagedList)
        `when`(fakeDummy.data?.size).thenReturn(1)
        val tv = MutableLiveData<Resource<PagedList<TvShowResult>>>()
        tv.value = fakeDummy

        `when`(repository.getTvShowTop()).thenReturn(tv)
        val entity =viewModel.getTvShow().value?.data
        verify(repository).getTvShowTop()
        assertNotNull(entity)
        assertEquals(1, entity?.size)

        viewModel.getTvShow().observeForever(observer)
        verify(observer).onChanged(fakeDummy)
    }
}