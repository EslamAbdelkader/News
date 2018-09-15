package com.eslam.news.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.eslam.news.R

class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.findViewById<TextView>(R.id.title)
}
