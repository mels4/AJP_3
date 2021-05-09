package com.example.android.ajp_1.ui.movie

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.android.ajp_1.R
import com.example.android.ajp_1.ui.movie.MovieUpcoming.MovieUpcomingFragment
import com.example.android.ajp_1.ui.movie.moviePopular.MovieFragment
import com.example.android.ajp_1.ui.movie.movieTopRated.MovieTopFragment

class SectionPagerMovieAdapter(private val mContext: Context, fm : FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object{
        @StringRes
        private val TAB_TITLE = intArrayOf(R.string.movie_popular, R.string.movie_topRated, R.string.movie_upcoming)
    }

    override fun getCount(): Int= 3


    override fun getItem(position: Int): Fragment =
        when(position){
            0-> MovieFragment()
            1-> MovieTopFragment()
            2-> MovieUpcomingFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence =
        mContext.resources.getString(TAB_TITLE[position])
}