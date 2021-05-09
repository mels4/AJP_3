package com.example.android.ajp_1.ui.favorite.tv

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
import com.example.android.ajp_1.entity.remote.response.TvShowResult
import com.example.android.ajp_1.ui.detail.DetailTvShowActivity
import kotlinx.android.synthetic.main.item_list.view.*

class TvFavoriteAdapter: PagedListAdapter<TvShowResult, TvFavoriteAdapter.TvViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK= object :DiffUtil.ItemCallback<TvShowResult>(){
            override fun areItemsTheSame(oldItem: TvShowResult, newItem: TvShowResult): Boolean {
                return oldItem.tvShowId == newItem.tvShowId
            }

            override fun areContentsTheSame(oldItem: TvShowResult, newItem: TvShowResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun getSwipedData(swipePosition: Int): TvShowResult? = getItem(swipePosition)

    class TvViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(tv: TvShowResult){
            with(itemView){
                tv_title.text = tv.title
                tv_desc.text = tv.decription
                tv_director.text = tv.date
                setOnClickListener {
                    val intent = Intent(itemView.context, DetailTvShowActivity::class.java)
                    intent.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, tv.tvShowId)
                    itemView.context.startActivity(intent)
                }
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+tv.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh))
                    .error(R.drawable.ic_baseline_broken_image)
                    .into(img_poster)
                ratingbar.rating =tv.user_score.toFloat()
                tv_userscore.text = tv.user_score.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvFavoriteAdapter.TvViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return TvViewHolder(view)
    }

    override fun onBindViewHolder(holder: TvFavoriteAdapter.TvViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null){
            holder.bind(tv)
        }
    }

}