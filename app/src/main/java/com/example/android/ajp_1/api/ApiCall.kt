package com.example.android.ajp_1.api

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.ajp_1.BuildConfig
import com.example.android.ajp_1.entity.remote.response.*
import com.example.android.ajp_1.utils.EspressoIdlingResource
import com.example.android.ajp_1.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiCall {
    private var LANGUAGE:String = "en-US"
    private lateinit var apiCall: ApiCall

    companion object{
        @Volatile
        private var instance: ApiCall?= null

        fun getInstance(): ApiCall=
            instance?: synchronized(this){
                instance?: ApiCall()
        }
    }

    fun getMovieTop(): LiveData<ApiResponse<List<MovieResponse>>>{
        EspressoIdlingResource.increment()
        val movie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        val call = ApiClient.service.getMovieTop(BuildConfig.ApiKey, LANGUAGE, 1)
        try {
            call.enqueue(object : Callback<MovieResult>{
                override fun onResponse(call: Call<MovieResult>, response: Response<MovieResult>) {
                    if (response.isSuccessful) {
                        val responses = response.body()?.let { ApiResponse.success(it.result) }
                        movie.postValue(responses)
                    }
                }
                override fun onFailure(call: Call<MovieResult>, t: Throwable) {
                    Log.d("Movie", t.message.toString())
                }
            })
        }catch (e: Exception){
            e.printStackTrace()
        }
        EspressoIdlingResource.decrement()
        return movie

    }

    fun getMovieTopRated(): LiveData<ApiResponse<List<MovieResponse>>>{
        EspressoIdlingResource.increment()
        val movie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        val call = ApiClient.service.getMovieTopRate(BuildConfig.ApiKey, LANGUAGE, 1)
        try {
            call.enqueue(object : Callback<MovieResult>{
                override fun onFailure(call: Call<MovieResult>, t: Throwable) {
                    Log.d("movie Top Rated", t.message.toString())
                }

                override fun onResponse(call: Call<MovieResult>, response: Response<MovieResult>) {
                    if (response.isSuccessful){
                        val responses = response.body()?.let { ApiResponse.success(it.result) }
                        movie.postValue(responses)
                    }
                }
            })
        }catch (e: Exception){
            e.printStackTrace()
        }

        EspressoIdlingResource.decrement()
        return movie
    }

    fun getMovieUpcoming(): LiveData<ApiResponse<List<MovieResponse>>>{
        EspressoIdlingResource.increment()
        val movie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        val call = ApiClient.service.getMovieUpComing(BuildConfig.ApiKey, LANGUAGE, 1)
        try {
            call.enqueue(object : Callback<MovieResult>{
                override fun onFailure(call: Call<MovieResult>, t: Throwable) {
                    Log.d("movie Top Rated", t.message.toString())
                }

                override fun onResponse(call: Call<MovieResult>, response: Response<MovieResult>) {
                    if (response.isSuccessful){
                        val responses = response.body()?.let { ApiResponse.success(it.result) }
                        movie.postValue(responses)
                    }
                }
            })
        }catch (e: Exception){
            e.printStackTrace()
        }

        EspressoIdlingResource.decrement()
        return movie
    }
    
    
    fun getDetailMovie(id: String): LiveData<ApiResponse<MovieResponse>>{
        EspressoIdlingResource.increment()
        val detailResult = MutableLiveData<ApiResponse<MovieResponse>>()
        val call = ApiClient.service.getDetailMovie(id, BuildConfig.ApiKey, LANGUAGE)
        try {
            call.enqueue(object : Callback<MovieResponse>{
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    if (response.isSuccessful){
                        val responses = response.body()?.let { ApiResponse.success(it) }
                        detailResult.postValue(responses)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("detail Movie", t.message.toString())
                }
            })
        }catch (e: Exception){
            e.printStackTrace()
        }
        EspressoIdlingResource.decrement()
        return detailResult

    }
    fun getTvShowTop(): LiveData<ApiResponse<List<TvShowResult>>> {
        EspressoIdlingResource.increment()
        val tv = MutableLiveData<ApiResponse<List<TvShowResult>>>()
        val call = ApiClient.service.getTvShowTop(BuildConfig.ApiKey, LANGUAGE, 1)
        try {
            call.enqueue(object : Callback<TvShowResponse> {
                override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val responses = response.body()?.let { ApiResponse.success(it.results) }
                        tv.postValue(responses)
                    }
                }

                override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                    Log.d("Tv SHow", t.message.toString())
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
        EspressoIdlingResource.decrement()
        return tv
    }
    fun getDetailTvShow(id: String): LiveData<ApiResponse<TvShowResult>>{
       EspressoIdlingResource.increment()
        val detailTv = MutableLiveData<ApiResponse<TvShowResult>>()
        val call = ApiClient.service.getDetailTvShow(id, BuildConfig.ApiKey, LANGUAGE)
        try {
            call.enqueue(object :Callback<TvShowResult>{
                override fun onResponse(call: Call<TvShowResult>, response: Response<TvShowResult>) {
                    if (response.isSuccessful){
                        val responses = response.body()?.let { ApiResponse.success(it) }
                        detailTv.postValue(responses)
                    }
                }

                override fun onFailure(call: Call<TvShowResult>?, t: Throwable?) {
                    Log.d("TvShowDetail", t?.message.toString())
                }
            })
        }catch (e: Exception){
            e.printStackTrace()
        }
        EspressoIdlingResource.decrement()
        return detailTv
    }

    fun getSearchMovies(query: String): LiveData<List<MovieResponse>>{
        EspressoIdlingResource.increment()
        val querys = MutableLiveData<List<MovieResponse>>()
        val call = ApiClient.service.getSearchMovie(BuildConfig.ApiKey, LANGUAGE, query, 1, false)
        try {
            call.enqueue(object : Callback<MovieResult>{
                override fun onResponse(call: Call<MovieResult>, response: Response<MovieResult>) {
                    if (response.isSuccessful){
                        val responses = response.body()?.result
                        querys.postValue(responses)
                    }
                }

                override fun onFailure(call: Call<MovieResult>, t: Throwable) {
                    Log.d("Search", t.message.toString())
                }
            })
        }catch (e: Exception){
            e.printStackTrace()
        }
        EspressoIdlingResource.decrement()
        return querys
    }

}