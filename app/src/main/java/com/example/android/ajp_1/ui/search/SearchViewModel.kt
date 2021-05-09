package com.example.android.ajp_1.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.ajp_1.entity.remote.Repository
import com.example.android.ajp_1.entity.remote.response.MovieResponse

class SearchViewModel(private val repository: Repository): ViewModel() {

    fun getSearch(query: String): LiveData<List<MovieResponse>> = repository.getSearchMovie(query)
}