package com.eslam.news.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.eslam.news.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = ArticlesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val articlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
        articlesViewModel.articles.observe(this, Observer { adapter.submitList(it) })
        articlesViewModel.networkState.observe(this, Observer { adapter.setNetworkState(it!!) })
    }
}

