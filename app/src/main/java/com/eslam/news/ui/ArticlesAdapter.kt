package com.eslam.news.ui

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.eslam.news.R
import com.eslam.news.model.Article

class ArticlesAdapter : PagedListAdapter<Article, ArticleViewHolder>(ARTICLE_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.article_row, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.title.text = getItem(position)?.title
    }


    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                    oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                    oldItem == newItem
        }
    }
}