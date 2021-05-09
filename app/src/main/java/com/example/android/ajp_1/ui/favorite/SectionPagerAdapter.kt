package com.example.android.ajp_1.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.android.ajp_1.R
import com.example.android.ajp_1.ui.favorite.movie.MovieFavoriteFragment
import com.example.android.ajp_1.ui.favorite.tv.TvFavoriteFragment

class SectionPagerAdapter(private val mContext: Context, fm : FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object{
        @StringRes
        private val TAB_TITLE = intArrayOf(R.string.title_movie, R.string.title_tvshow)
    }


    override fun getCount(): Int= 2

    override fun getPageTitle(position: Int): CharSequence? = mContext.resources.getString(TAB_TITLE[position])

    override fun getItem(position: Int): Fragment =
        when(position){
            0-> MovieFavoriteFragment()
            1-> TvFavoriteFragment()
            else -> Fragment()
        }

}