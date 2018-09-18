package com.eslam.news.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.eslam.news.R
import com.eslam.news.di.Injection
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val articlesViewModel: ArticlesViewModel by lazy {
        ViewModelProviders.of(this, Injection.provideViewModelFactory()).get(ArticlesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        articlesViewModel.favorites.observe(this, Observer {
            showContent()
            exitFullScreen()
            articlesViewModel.favorites.removeObservers(this)
        })
        enterFullScreen()
    }

    private fun showContent() {
        splash.visibility = View.GONE
        setupViewPager()
    }

    private fun setupViewPager() {
        viewPager.visibility = View.VISIBLE
        viewPager.adapter = MainViewPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.setIcon(R.drawable.news)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.starred)
    }


    private fun enterFullScreen() {
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    private fun exitFullScreen() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }


}

