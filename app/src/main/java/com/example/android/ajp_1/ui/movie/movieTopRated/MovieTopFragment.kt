package com.example.android.ajp_1.ui.movie.movieTopRated

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.ajp_1.R
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.ui.movie.MovieAdapter
import com.example.android.ajp_1.ui.movie.MovieViewModel
import com.example.android.ajp_1.utils.SortUtils
import com.example.android.ajp_1.viemodel.ViewModelFactory
import com.example.android.ajp_1.vo.Resource
import com.example.android.ajp_1.vo.Status
import kotlinx.android.synthetic.main.fragment_movie_top.*

class MovieTopFragment : Fragment() {


    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_top, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        viewModel.getMovieTopRated(SortUtils.NEWEST).observe(viewLifecycleOwner, movieObserver)

        with(rv_movieTop){
            movieAdapter= MovieAdapter()
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
            setHasFixedSize(true)
        }
    }

    private val movieObserver = Observer<Resource<PagedList<MovieResponse>>>{list->
        if (list != null){
            when(list.status){
                Status.LOADING -> progressbar.visibility = View.VISIBLE
                Status.SUCCESS ->{
                    movieAdapter.submitList(list.data)
                    progressbar.visibility = View.GONE
                }
                Status.ERROR ->{
                    progressbar.visibility = View.GONE
                    Toast.makeText(context,"Erorr", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}