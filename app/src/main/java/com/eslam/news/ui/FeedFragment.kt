package com.eslam.news.ui


import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eslam.news.R
import com.eslam.news.di.Injection
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    private lateinit var adapter: ArticlesAdapter
    private val articlesViewModel: ArticlesViewModel by lazy {
        ViewModelProviders.of(activity!!, Injection.provideViewModelFactory()).get(ArticlesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = ArticlesAdapter(articlesViewModel)
        recyclerView.adapter = adapter
        articlesViewModel.articles.observe(this, Observer {
            adapter.submitList(it)
        })
        articlesViewModel.networkState.observe(this as LifecycleOwner, Observer { adapter.setNetworkState(it!!) })
        articlesViewModel.favorites.observe(this as LifecycleOwner, Observer { adapter.notifyDataSetChanged() })
        setupScrollListener()
    }


    private fun setupScrollListener() {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                articlesViewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }
}
