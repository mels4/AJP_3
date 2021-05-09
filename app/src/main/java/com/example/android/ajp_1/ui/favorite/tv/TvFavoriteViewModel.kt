package com.example.android.ajp_1.ui.favorite.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.android.ajp_1.entity.remote.Repository
import com.example.android.ajp_1.entity.remote.response.TvShowResult

class TvFavoriteViewModel(private val repository: Repository) : ViewModel() {
    fun getFavorite(): LiveData<PagedList<TvShowResult>> = repository.getFavoriteTv()

    fun setFavorite (tvShowResult: TvShowResult){
        val newState = !tvShowResult.favorites
        repository.setTvFavorite(tvShowResult, newState)
    }
}