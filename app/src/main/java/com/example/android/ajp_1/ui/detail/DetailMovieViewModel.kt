package com.example.android.ajp_1.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.ajp_1.entity.remote.Repository
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.entity.remote.response.TvShowResult
import com.example.android.ajp_1.vo.Resource

class DetailMovieViewModel(private val repository: Repository): ViewModel() {
    var movieId = MutableLiveData<String>()
    private lateinit var id: String

    fun setSelectedMovie(movieId: String){
        this.movieId.value = movieId
    }

    var getDetailMovie: LiveData<Resource<MovieResponse>> = Transformations.switchMap(movieId) { mMovieId->
        repository.getDetailMovie(mMovieId)
    }

    var getDetailTvShow : LiveData<Resource<TvShowResult>> = Transformations.switchMap(movieId) { mTvId ->
        repository.getDetailTv(mTvId)
    }

    fun setFavorite(){
        if (getDetailMovie.value != null ){
            val movieResult = getDetailMovie.value?.data
            if (movieResult!=null) {
                val state = !movieResult.favorite
                repository.setMovieFavorite(movieResult, state)
            }
        }
    }

    fun setFavoriteTv(){
        if (getDetailTvShow.value != null){
            val tvResult = getDetailTvShow.value?.data
            if (tvResult != null){
                val state = !tvResult.favorites
                repository.setTvFavorite(tvResult, state)
            }
        }
    }
}