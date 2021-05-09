package com.example.android.ajp_1.ui.tvShow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.ajp_1.R
import com.example.android.ajp_1.viemodel.ViewModelFactory
import com.example.android.ajp_1.vo.Status
import kotlinx.android.synthetic.main.fragment_tvshow.*

class TvShowFragment : Fragment() {
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_tvshow, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressbartv.visibility = View.VISIBLE
        if (activity != null){
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
            viewModel.getTvShow().observe(viewLifecycleOwner, {list->
                if (list!= null){
                    when(list.status){
                        Status.LOADING -> progressbartv.visibility = View.VISIBLE
                        Status.SUCCESS ->{
                            tvShowAdapter.submitList(list.data)
                            tvShowAdapter.notifyDataSetChanged()
                            progressbartv.visibility = View.GONE

                        }
                        Status.ERROR ->{
                            progressbartv.visibility = View.GONE
                            Toast.makeText(context,"error", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            })
            

            with(rv_tvShow){
                tvShowAdapter = TvShowAdapter()
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
        }
    }
}