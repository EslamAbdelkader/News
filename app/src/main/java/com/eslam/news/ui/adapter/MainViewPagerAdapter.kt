package com.eslam.news.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.eslam.news.ui.fragment.FavoriteFragment
import com.eslam.news.ui.fragment.FeedFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(p0: Int): Fragment {
        return when(p0){
            0-> FeedFragment()
            1-> FavoriteFragment()
            else-> Fragment()
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0->"Feed"
            1->"Favorites"
            else->""
        }
    }
}