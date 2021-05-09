package com.example.android.ajp_1.ui.tvShow


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.android.ajp_1.entity.remote.Repository
import com.example.android.ajp_1.entity.remote.response.TvShowResult
import com.example.android.ajp_1.vo.Resource

class TvShowViewModel(private val repository: Repository) : ViewModel() {

    fun getTvShow(): LiveData<Resource<PagedList<TvShowResult>>> = repository.getTvShowTop()
}