package com.example.android.ajp_1.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.android.ajp_1.entity.remote.Repository
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.vo.Resource

class MovieViewModel(private val repository: Repository) : ViewModel() {

    fun getMoviePopular(sort: String): LiveData<Resource<PagedList<MovieResponse>>> = repository.getMovieTop(sort)

    fun getMovieTopRated(sort: String): LiveData<Resource<PagedList<MovieResponse>>> = repository.getMovieTopRated(sort)

    fun getMovieUpcoming(sort: String): LiveData<Resource<PagedList<MovieResponse>>> = repository.getMovieUpcoming(sort)

}