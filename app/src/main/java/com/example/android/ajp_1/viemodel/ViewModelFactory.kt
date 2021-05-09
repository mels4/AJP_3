package com.example.android.ajp_1.viemodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.ajp_1.di.Injection
import com.example.android.ajp_1.entity.remote.Repository
import com.example.android.ajp_1.ui.detail.DetailMovieViewModel
import com.example.android.ajp_1.ui.favorite.movie.MovieFavoriteViewModel
import com.example.android.ajp_1.ui.favorite.tv.TvFavoriteViewModel
import com.example.android.ajp_1.ui.movie.MovieViewModel
import com.example.android.ajp_1.ui.search.SearchViewModel
import com.example.android.ajp_1.ui.tvShow.TvShowViewModel

class ViewModelFactory private constructor(private val repository: Repository):
    ViewModelProvider.NewInstanceFactory() {

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context)=
            instance?: synchronized(this){
                instance?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(TvShowViewModel::class.java)->{
                return TvShowViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MovieViewModel::class.java)->{
                return MovieViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java)->{
                return DetailMovieViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MovieFavoriteViewModel::class.java)->{
                return MovieFavoriteViewModel(repository) as T
            }
            modelClass.isAssignableFrom(TvFavoriteViewModel::class.java)->{
                return TvFavoriteViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java)->{
                return SearchViewModel(repository) as T
            }
        }
        return super.create(modelClass)
    }
}