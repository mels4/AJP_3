package com.example.android.ajp_1.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.ajp_1.R
import kotlinx.android.synthetic.main.fragment_movie_home.*


class MovieHomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sectionPagerMovieAdapter = SectionPagerMovieAdapter(requireActivity(), requireFragmentManager())
        viewPager_movie.adapter = sectionPagerMovieAdapter
        tabslayout_movie.setupWithViewPager(viewPager_movie)

    }

}