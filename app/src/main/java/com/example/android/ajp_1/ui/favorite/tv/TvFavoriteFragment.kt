package com.example.android.ajp_1.ui.favorite.tv

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
import kotlinx.android.synthetic.main.fragment_tv_favorite.*

class TvFavoriteFragment : Fragment() {
    
    private lateinit var tvAdapter: TvFavoriteAdapter
    private lateinit var viewModel: TvFavoriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        itemTouchHelper.attachToRecyclerView(rv_favoriteTv)
        
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[TvFavoriteViewModel::class.java]
        progressbar.visibility = View.VISIBLE
        tvAdapter= TvFavoriteAdapter()
        if (activity!=null){
            viewModel.getFavorite().observe(viewLifecycleOwner,{tv->
                tvAdapter.submitList(tv)
                progressbar.visibility = View.GONE
            })

            with(rv_favoriteTv){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvAdapter
            }
        }

    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null){
                val swipedPosition = viewHolder.adapterPosition
                val tvResult = tvAdapter.getSwipedData(swipedPosition)
                tvResult?.let { viewModel.setFavorite(it) }

                val snakbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_SHORT)
                snakbar.setAction(R.string.message_ok){
                    tvResult?.let { viewModel.setFavorite(it) }
                }
            }
        }
    })

}