package com.example.android.ajp_1.ui.favorite.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.ajp_1.R
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.ui.detail.DetailMovieActivity
import kotlinx.android.synthetic.main.item_list.view.*

class MovieFavoriteAdapter : PagedListAdapter<MovieResponse, MovieFavoriteAdapter.MovieHolder>(DIFF_CALLBACK){

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieResponse>(){
            override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemFavorite = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MovieHolder(itemFavorite)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movies = getItem(position)
        if (movies!= null){
            holder.bind(movies)
        }
    }


    fun getSwipeData(swipePosition: Int): MovieResponse? = getItem(swipePosition)

    class MovieHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(movie: MovieResponse) {
            with(itemView) {
                tv_title.text = movie.title
                tv_desc.text = movie.description
                tv_director.text = movie.rilis
                ratingbar.rating = movie.userScore.toFloat()

                setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_POSTER, movie.movieId)
                    itemView.context.startActivity(intent)
                }
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + movie.imgPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh))
                    .error(R.drawable.ic_baseline_broken_image)
                    .into(img_poster)
                tv_userscore.text = movie.userScore.toString()
            }
        }
    }

}