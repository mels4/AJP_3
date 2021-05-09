package com.example.android.ajp_1.di


import android.content.Context
import com.example.android.ajp_1.api.ApiCall
import com.example.android.ajp_1.entity.local.LocalDataSource
import com.example.android.ajp_1.entity.local.database.MovieDatabase
import com.example.android.ajp_1.entity.remote.Repository
import com.example.android.ajp_1.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context):  Repository{

        val database = MovieDatabase.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors =AppExecutors()
        val apiCall = ApiCall.getInstance()
        return Repository.getInstance(apiCall, localDataSource, appExecutors)
    }
}