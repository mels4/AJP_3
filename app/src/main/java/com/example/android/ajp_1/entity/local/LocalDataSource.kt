package com.example.android.ajp_1.entity.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.android.ajp_1.entity.local.database.MovieDao
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.entity.remote.response.TvShowResult
import com.example.android.ajp_1.utils.SortUtils

class LocalDataSource private constructor(private val mMovieDao: MovieDao){

    companion object{
        private var INSTANCE :LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource=
            INSTANCE?: LocalDataSource(movieDao)
    }


    fun getTvRoom(): DataSource.Factory<Int, TvShowResult> = mMovieDao.getTv()

    fun getMovieRoom(sort: String): DataSource.Factory<Int, MovieResponse> {
        val query = SortUtils.getSortedQueryMovie(sort)
        return mMovieDao.getMovie(query)
    }

    fun getMovieById(movieId: String): LiveData<MovieResponse> = mMovieDao.getMovieById(movieId)

    fun getTvByIDRoom(tvId: String): LiveData<TvShowResult> = mMovieDao.getTvById(tvId)

    fun insertMovie(movie: List<MovieResponse>) = mMovieDao.insertMovie(movie)

    fun insertTv(tv: List<TvShowResult>) = mMovieDao.insertTv(tv)

    fun setMovieFavorite(movie: MovieResponse, newState: Boolean){
        movie.favorite = newState
        mMovieDao.updateMovie(movie)
    }

    fun setTvFavorite(tv: TvShowResult, newState: Boolean){
        tv.favorites = newState
        mMovieDao.updateTv(tv)
    }

    fun getFavoriteMovie() : DataSource.Factory<Int, MovieResponse> = mMovieDao.getFavoriteMovie()

    fun getFavoriteTv(): DataSource.Factory<Int, TvShowResult> = mMovieDao.getFavoriteTv()

    //fun getSearchMovie(query: String): DataSource.Factory<Int, TvShowResult> = mMovieDao.getMovie(query)
}