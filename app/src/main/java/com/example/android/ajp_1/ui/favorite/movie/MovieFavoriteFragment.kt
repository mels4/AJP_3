package com.example.android.ajp_1.ui.favorite.movie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.ajp_1.R
import com.example.android.ajp_1.viemodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie_favorite.*

class MovieFavoriteFragment : Fragment() {

    private lateinit var viewModel: MovieFavoriteViewModel
    private lateinit var movieFavoriteAdapter: MovieFavoriteAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        itemTouchHelper.attachToRecyclerView(rv_favoriteMovie)



        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[MovieFavoriteViewModel::class.java]
            movieFavoriteAdapter = MovieFavoriteAdapter()
            progressBar.visibility = View.VISIBLE
            viewModel.getFavorite().observe(viewLifecycleOwner, { movies ->
                    movieFavoriteAdapter.submitList(movies)
                    progressBar.visibility = View.GONE

            })

            with(rv_favoriteMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieFavoriteAdapter
            }
        }
    }

    private val itemTouchHelper= ItemTouchHelper(object : ItemTouchHelper.Callback(){
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean =
            true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null){
                val swipedPosition = viewHolder.adapterPosition
                val movieResponse = movieFavoriteAdapter.getSwipeData(swipedPosition)
                movieResponse?.let { viewModel.setFavorite(it) }

                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_SHORT)
                snackbar.setAction(R.string.message_ok){
                    movieResponse?.let { viewModel.setFavorite(it) }
                }
                snackbar.show()
            }
        }
    })

}