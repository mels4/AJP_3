package com.example.android.ajp_1.ui.movie.MovieUpcoming

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.ajp_1.R
import com.example.android.ajp_1.ui.movie.MovieAdapter
import com.example.android.ajp_1.ui.movie.MovieViewModel
import com.example.android.ajp_1.utils.SortUtils
import com.example.android.ajp_1.viemodel.ViewModelFactory
import com.example.android.ajp_1.vo.Status
import kotlinx.android.synthetic.main.fragment_movie_top.progressbar
import kotlinx.android.synthetic.main.fragment_movie_upcoming.*

class MovieUpcomingFragment : Fragment() {

    private lateinit var adapterMovie: MovieAdapter
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_upcoming, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        viewModel.getMovieUpcoming(SortUtils.NEWEST).observe(viewLifecycleOwner, { list->
            if (list != null) {
                when (list.status) {
                    Status.LOADING -> progressbar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        adapterMovie.submitList(list.data)
                        progressbar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        progressbar.visibility = View.GONE
                        Toast.makeText(context, "Erorr", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        with(rv_movieUp){
            layoutManager = LinearLayoutManager(context)
            adapterMovie = MovieAdapter()
            adapter = adapterMovie
            setHasFixedSize(true)
        }
        }

}