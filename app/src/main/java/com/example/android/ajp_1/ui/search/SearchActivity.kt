package com.example.android.ajp_1.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.ajp_1.R
import com.example.android.ajp_1.viemodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    private lateinit var adapterSearch: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        btn_back.setBackgroundResource(0)
        btn_back.setOnClickListener {
            onBackPressed()
            finish()
        }
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]
        edt_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                val query = v.text.toString()
                viewModel.getSearch(query).observe(this,{ list->
                    adapterSearch.selectedSearch(list)
                })
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }


        with(rv_search){
            adapterSearch = SearchAdapter()
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = adapterSearch
            setHasFixedSize(true)
        }
    }
}