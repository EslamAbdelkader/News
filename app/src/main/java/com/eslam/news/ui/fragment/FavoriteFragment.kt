package com.eslam.news.ui.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eslam.news.R
import com.eslam.news.di.Injection
import com.eslam.news.ui.viewmodel.ArticlesViewModel
import com.eslam.news.ui.adapter.FavoriteAdapter
import kotlinx.android.synthetic.main.fragment_feed.*

class FavoriteFragment : Fragment() {

    private val adapter by lazy { FavoriteAdapter(articlesViewModel) }
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
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = null
        articlesViewModel.favorites.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
