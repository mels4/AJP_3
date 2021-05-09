package com.example.android.ajp_1.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.ajp_1.R
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.viemodel.ViewModelFactory
import com.example.android.ajp_1.vo.Status
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.android.synthetic.main.content_detail.btn_bck_movie
import kotlinx.android.synthetic.main.content_detail.img_poster_movie
import kotlinx.android.synthetic.main.content_detail.img_poster_bgr
import kotlinx.android.synthetic.main.content_detail.tv_desc
import kotlinx.android.synthetic.main.content_detail.tv_rilis
import kotlinx.android.synthetic.main.content_detail.tv_title_movie

class DetailMovieActivity : AppCompatActivity(), View.OnClickListener{

    private var isFavorite: Boolean = false
    private lateinit var viewModel: DetailMovieViewModel

    companion object{
        const val EXTRA_POSTER ="extra_poster"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        btn_bck_movie.setBackgroundResource(0)
        btn_share_movie.setBackgroundResource(0)
        

        btn_favorite.setOnClickListener(this)
        btn_bck_movie.setOnClickListener(this)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        progressbar.visibility = View.VISIBLE

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getString(EXTRA_POSTER)
            if (movieId != null) {
                viewModel.setSelectedMovie(movieId)
                viewModel.getDetailMovie.observe(this, {movieById->
                    if (movieById !=null){
                        when(movieById.status){
                            Status.LOADING -> progressbar.visibility =View.VISIBLE
                            Status.SUCCESS -> if (movieById.data != null){

                                getDetail(movieById.data)
                                progressbar.visibility = View.GONE

                                val state = movieById.data.favorite
                                setFavorite(state)

                            }
                            Status.ERROR ->{
                                progressbar.visibility = View.GONE
                                Toast.makeText(applicationContext,  "Sorry Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getDetail(movie: MovieResponse){
        tv_title_movie.text = movie.title
        tv_rilis.text = movie.rilis
        tv_desc.text = movie.description
        tv_tagline.text = "#"+movie.tageline
        rt_userScore.rating = movie.userScore.toFloat()
        tv_genre.text = movie.userScore.toString()
        tv_status.text = movie.status
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+movie.imgPath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh))
            .error(R.drawable.ic_baseline_broken_image)
            .into(img_poster_movie)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w533_and_h300_bestv2"+movie.imgPathBgr)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh))
            .error(R.drawable.ic_baseline_broken_image)
            .into(img_poster_bgr)
        btn_share_movie.setOnClickListener{
            val intentshare =Intent(Intent.ACTION_SEND)
            intentshare.putExtra(Intent.EXTRA_TEXT, "visit the link for information \nhttps://www.themoviedb.org/movie/"+ movie.movieId)
            intentshare.type = "text/plain"
            startActivity(intentshare)
        }
    }
    private fun setFavorite(state: Boolean){
        if(state){
            btn_favorite.setImageResource(R.drawable.ic_baseline_favorite)
        }else{
            btn_favorite.setImageResource(R.drawable.ic_baseline_favorite_border)
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_bck_movie->{
                onBackPressed()
                finish()
            }

            R.id.btn_favorite->{
                if (!isFavorite){
                    viewModel.setFavorite()
                    isFavorite = true
                    setFavorite(isFavorite)
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                }else{
                    isFavorite= false
                    setFavorite(isFavorite)
                    Toast.makeText(this, "remove", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}