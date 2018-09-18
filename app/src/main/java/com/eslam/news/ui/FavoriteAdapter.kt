package com.eslam.news.ui

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.eslam.news.R
import com.eslam.news.model.Article
import com.eslam.news.utils.ArticleComparator

class FavoriteAdapter(private val viewModel: ArticlesViewModel) : ListAdapter<Article, ArticleViewHolder>(ArticleComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
            ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.article_row, parent, false))

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.star.setOnClickListener {
            viewModel.toggleFavorite(item)
        }
    }

}
