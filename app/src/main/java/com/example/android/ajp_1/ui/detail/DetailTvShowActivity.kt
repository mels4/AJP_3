package com.example.android.ajp_1.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.ajp_1.R
import com.example.android.ajp_1.entity.remote.response.TvShowResult
import com.example.android.ajp_1.viemodel.ViewModelFactory
import com.example.android.ajp_1.vo.Status
import kotlinx.android.synthetic.main.content_detailtvshow.*
import kotlinx.android.synthetic.main.content_detailtvshow.btn_bck_tvshow
import kotlinx.android.synthetic.main.content_detailtvshow.img_poster
import kotlinx.android.synthetic.main.content_detailtvshow.img_poster_bgr
import kotlinx.android.synthetic.main.content_detailtvshow.tv_desc
import kotlinx.android.synthetic.main.content_detailtvshow.tv_title

class DetailTvShowActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: DetailMovieViewModel
    private var isFavorite: Boolean = false

    companion object{
        const val EXTRA_TVSHOW = "extra_tvshows"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv_show)

        getInitialise()

        val factory = ViewModelFactory.getInstance(this)
        viewModel= ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        progressbar_tvShow.visibility = View.VISIBLE

        val extras = intent.extras
        if (extras != null){
            val tvShowId = extras.getString(EXTRA_TVSHOW)
            if (tvShowId != null){
                progressbar_tvShow.visibility = View.GONE
                viewModel.setSelectedMovie(tvShowId)
                viewModel.getDetailTvShow.observe(this, { tv ->
                    if (tv != null){
                        when(tv.status){
                            Status.LOADING -> progressbar_tvShow.visibility = View.VISIBLE
                            Status.SUCCESS -> if (tv.data != null){
                                getDetail(tv.data)
                                progressbar_tvShow.visibility = View.GONE

                                val state = tv.data.favorites
                                setFavorite(state)
                            }
                            Status.ERROR ->{
                                progressbar_tvShow.visibility = View.GONE
                                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
                }
            }

        }

    @SuppressLint("SetTextI18n")
    private fun getDetail(tvShow: TvShowResult){
        tv_title.text = tvShow.title
        tv_desc.text = tvShow.decription
        tv_tagline.text = "#"+tvShow.tagline
        tv_episode.text = tvShow.nEpisode.toString()+" Episode"
        tv_season.text = tvShow.nSeason+" Seasons"

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+tvShow.imagePath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh))
            .error(R.drawable.ic_baseline_broken_image)
            .into(img_poster)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w533_and_h300_bestv2"+tvShow.imagePath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh))
            .error(R.drawable.ic_baseline_broken_image)
            .into(img_poster_bgr)
        btn_share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT,"visit the link for information \nhttps://www.themoviedb.org/tv/"+tvShow.tvShowId)
            startActivity(intent)
        }
    }

    private fun getInitialise(){
        btn_bck_tvshow.setBackgroundResource(0)
        btn_share.setBackgroundResource(0)

        btn_bck_tvshow.setOnClickListener(this)
        btn_favoriteTv.setOnClickListener(this)
    }

    private fun setFavorite(state: Boolean){
        if (state){
            btn_favoriteTv.setImageResource(R.drawable.ic_baseline_favorite)
        }else{
            btn_favoriteTv.setImageResource(R.drawable.ic_baseline_favorite_border)
        }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_bck_tvshow-> {
                onBackPressed()
                finish()
            }
            R.id.btn_favoriteTv->{
                if (!isFavorite){
                    viewModel.setFavoriteTv()
                    isFavorite = true
                    setFavorite(isFavorite)
                }else{
                    isFavorite = false
                    setFavorite(isFavorite)
                }
            }


        }
    }
}