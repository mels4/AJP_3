package com.example.android.ajp_1.entity.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.entity.remote.response.TvShowResult
import com.example.android.ajp_1.vo.Resource

interface CatalogueDataSource{


    fun getMovieTop(sort: String): LiveData<Resource<PagedList<MovieResponse>>>

    fun getMovieTopRated(sort: String): LiveData<Resource<PagedList<MovieResponse>>>

    fun getMovieUpcoming(sort: String): LiveData<Resource<PagedList<MovieResponse>>>

    fun getTvShowTop(): LiveData<Resource<PagedList<TvShowResult>>>

    fun getFavoriteMovie(): LiveData<PagedList<MovieResponse>>

    fun getFavoriteTv(): LiveData<PagedList<TvShowResult>>

    fun getDetailMovie(movie_id: String): LiveData<Resource<MovieResponse>>

    fun getDetailTv(tv_id: String): LiveData<Resource<TvShowResult>>

    fun setMovieFavorite(movie: MovieResponse, state: Boolean)

    fun setTvFavorite(tv: TvShowResult, state: Boolean)

    fun getMovieByIdRoom(id: String): LiveData<MovieResponse>

    fun getSearchMovie(query: String): LiveData<List<MovieResponse>>


}