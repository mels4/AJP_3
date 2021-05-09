package com.example.android.ajp_1.entity.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android.ajp_1.api.ApiCall
import com.example.android.ajp_1.api.ApiResponse
import com.example.android.ajp_1.entity.local.LocalDataSource
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.entity.remote.response.TvShowResult
import com.example.android.ajp_1.utils.AppExecutors
import com.example.android.ajp_1.vo.NetworkBoundResource
import com.example.android.ajp_1.vo.Resource

class FakeRepository(private val apiCall: ApiCall,
                     private val localDataSource: LocalDataSource,
                     private val appExecutors: AppExecutors)
    : CatalogueDataSource {


    override fun getMovieTop(sort: String): LiveData<Resource<PagedList<MovieResponse>>> {
        return object: NetworkBoundResource<PagedList<MovieResponse>, List<MovieResponse>>(appExecutors){

            public override fun loadFromDB(): LiveData<PagedList<MovieResponse>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovieRoom(sort), config).build()
            }


            override fun shouldFetch(data: PagedList<MovieResponse>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> = apiCall.getMovieTop()

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieResponse>()
                for (response in data){
                    val movie = MovieResponse(response.movieId,
                        response.title,
                        response.imgPath,
                        response.imgPathBgr,
                        response.description,
                        response.rilis,
                        response.userScore,
                        response.tageline,
                        response.status,
                        false)
                    movieList.add(movie)
                }
                localDataSource.insertMovie(movieList)
            }

        }.asLiveData()

    }

    override fun getMovieTopRated(sort: String): LiveData<Resource<PagedList<MovieResponse>>> {
        return object : NetworkBoundResource<PagedList<MovieResponse>, List<MovieResponse>>(appExecutors){
            public override fun loadFromDB(): LiveData<PagedList<MovieResponse>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovieRoom(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieResponse>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> = apiCall.getMovieTopRated()

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieResponse>()
                for(response in data){
                    val movie = MovieResponse(response.movieId,
                        response.title,
                        response.imgPath,
                        response.imgPathBgr,
                        response.description,
                        response.rilis,
                        response.userScore,
                        response.tageline,
                        response.status,
                        false)
                    movieList.add(movie)
                }
                localDataSource.insertMovie(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieUpcoming(sort: String): LiveData<Resource<PagedList<MovieResponse>>> {
        return object : NetworkBoundResource<PagedList<MovieResponse>, List<MovieResponse>>(appExecutors){
            public override fun loadFromDB(): LiveData<PagedList<MovieResponse>> {
                val config = PagedList.Config.Builder()
                    .setInitialLoadSizeHint(4)
                    .setEnablePlaceholders(false)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovieRoom(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieResponse>?): Boolean =
                data==null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =apiCall.getMovieUpcoming()

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieResponse>()
                for(response in data){
                    val movie = MovieResponse(response.movieId,
                        response.title,
                        response.imgPath,
                        response.imgPathBgr,
                        response.description,
                        response.rilis,
                        response.userScore,
                        response.tageline,
                        response.status,
                        false)
                    movieList.add(movie)
                }
                localDataSource.insertMovie(movieList)
            }
        }.asLiveData()
    }


    override fun getDetailMovie(movie_id: String): LiveData<Resource<MovieResponse>> {
        return object : NetworkBoundResource<MovieResponse, MovieResponse>(appExecutors){
            override fun loadFromDB(): LiveData<MovieResponse> {
                return localDataSource.getMovieById(movie_id)
            }

            override fun shouldFetch(data: MovieResponse?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> {
                return apiCall.getDetailMovie(movie_id)
            }

            override fun saveCallResult(data: MovieResponse) {
            }
        }.asLiveData()
    }

    override fun getTvShowTop(): LiveData<Resource<PagedList<TvShowResult>>> {
        return object : NetworkBoundResource<PagedList<TvShowResult>, List<TvShowResult>>(appExecutors){
            public override fun loadFromDB(): LiveData<PagedList<TvShowResult>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvRoom(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowResult>?): Boolean =
                data == null || data.isEmpty()


            override fun createCall(): LiveData<ApiResponse<List<TvShowResult>>> = apiCall.getTvShowTop()


            override fun saveCallResult(data: List<TvShowResult>) {
                val tvList = ArrayList<TvShowResult>()
                for (response in data){
                    val tv = TvShowResult(response.tvShowId,
                        response.title,
                        response.decription,
                        response.imagePath,
                        response.user_score,
                        response.ImgPathBgr,
                        response.date,
                        response.nEpisode,
                        response.nSeason,
                        response.tagline,
                        false)
                    tvList.add(tv)
                }
                localDataSource.insertTv(tvList)
            }
        }.asLiveData()

    }

    override fun getDetailTv(tv_id: String): LiveData<Resource<TvShowResult>> {
        return object : NetworkBoundResource<TvShowResult, TvShowResult>(appExecutors){
            override fun loadFromDB(): LiveData<TvShowResult> {
                return localDataSource.getTvByIDRoom(tv_id)
            }

            override fun shouldFetch(data: TvShowResult?): Boolean {
                return data == null}

            override fun createCall(): LiveData<ApiResponse<TvShowResult>> =
                apiCall.getDetailTvShow(tv_id)

            override fun saveCallResult(data: TvShowResult) {
            }

        }.asLiveData()
    }

    override fun getMovieByIdRoom(id: String): LiveData<MovieResponse> {
        return localDataSource.getMovieById(id)
    }

    override fun setMovieFavorite(movie: MovieResponse, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movie, state) }


    override fun setTvFavorite(tv: TvShowResult, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setTvFavorite(tv, state) }
    }

    override fun getSearchMovie(query: String): LiveData<List<MovieResponse>> {
        return apiCall.getSearchMovies(query)
    }

    override fun getFavoriteMovie(): LiveData<PagedList<MovieResponse>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun getFavoriteTv(): LiveData<PagedList<TvShowResult>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getFavoriteTv(), config).build()
    }

}