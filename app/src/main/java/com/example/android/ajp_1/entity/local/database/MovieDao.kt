package com.example.android.ajp_1.entity.local.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.entity.remote.response.TvShowResult

@Dao
interface MovieDao {

    @RawQuery(observedEntities = [MovieResponse::class])
    fun getMovie(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieResponse>

    @Query("SELECT * FROM tvshowentities")
    fun getTv(): DataSource.Factory<Int, TvShowResult>

    @Query("SELECT * FROM movieentities WHERE id =:movieId")
    fun getMovieById(movieId: String): LiveData<MovieResponse>

    @Query("SELECT * FROM tvshowentities WHERE id =:tvShowId")
    fun getTvById(tvShowId: String): LiveData<TvShowResult>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: List<MovieResponse>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTv(tv: List<TvShowResult>)

    @Update
    fun updateMovie(movie: MovieResponse)

    @Update
    fun updateTv(tv: TvShowResult)

    @Query("SELECT * FROM movieentities where favorites = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, MovieResponse>

    @Query("SELECT * FROM tvshowentities where favorites =1")
    fun getFavoriteTv(): DataSource.Factory<Int, TvShowResult>
}