package com.example.android.ajp_1.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.android.ajp_1.entity.remote.Repository
import com.example.android.ajp_1.entity.remote.response.MovieResponse

class MovieFavoriteViewModel(private val repository: Repository) : ViewModel() {
    fun getFavorite(): LiveData<PagedList<MovieResponse>> = repository.getFavoriteMovie()

    fun setFavorite(movieResponse: MovieResponse){
        val newState = !movieResponse.favorite
        repository.setMovieFavorite(movieResponse, newState)
    }
}