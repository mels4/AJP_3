package com.example.android.ajp_1.ui.movie.moviePopular

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.ajp_1.R
import com.example.android.ajp_1.entity.remote.response.MovieResponse
import com.example.android.ajp_1.ui.movie.MovieAdapter
import com.example.android.ajp_1.ui.movie.MovieViewModel
import com.example.android.ajp_1.ui.search.SearchActivity
import com.example.android.ajp_1.utils.SortUtils
import com.example.android.ajp_1.viemodel.ViewModelFactory
import com.example.android.ajp_1.vo.Resource
import com.example.android.ajp_1.vo.Status
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_movie, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressbar.visibility = View.VISIBLE
        if (activity != null){
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            viewModel.getMoviePopular(SortUtils.NEWEST).observe(viewLifecycleOwner, movieObserver )

            with(rv_movie){
                movieAdapter = MovieAdapter()
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    private val movieObserver = Observer<Resource<PagedList<MovieResponse>>>{ list->
        if (list != null) {
            when (list.status) {
                Status.LOADING -> progressbar.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    movieAdapter.submitList(list.data)
                    movieAdapter.notifyDataSetChanged()
                    progressbar.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressbar.visibility = View.GONE
                    Toast.makeText(context, "Sorry is Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort =""
        when(item.itemId){
            R.id.action_newest -> sort = SortUtils.NEWEST
            R.id.action_oldest -> sort = SortUtils.OLDEST
            R.id.action_random -> sort = SortUtils.RANDOM
            R.id.actionSearch ->{
                startActivity(Intent(context, SearchActivity::class.java))
            }
        }
        viewModel.getMoviePopular(sort).observe(this, movieObserver)
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

}