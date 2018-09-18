package com.eslam.news.ui.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eslam.news.R
import com.eslam.news.model.Article
import com.eslam.news.model.NetworkState
import com.eslam.news.ui.holder.ArticleViewHolder
import com.eslam.news.ui.holder.NetworkViewHolder
import com.eslam.news.ui.viewmodel.ArticlesViewModel
import com.eslam.news.utils.ArticleComparator
import com.eslam.news.utils.isSameDate

class ArticlesAdapter(private val viewModel: ArticlesViewModel) : ListAdapter<Article, RecyclerView.ViewHolder>(ArticleComparator) {
    private var networkState: NetworkState = NetworkState.loading()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.article_row -> ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.article_row, parent, false))
            R.layout.network_state_item -> NetworkViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false))
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder) {
            val item = getItem(position)
            val previousItem = if(position>0) getItem(position - 1) else null
            holder.bind(item,position,previousItem)
            holder.star.setOnClickListener {
                viewModel.toggleFavorite(item)
                notifyItemChanged(position)
            }
        } else {
            (holder as NetworkViewHolder).bind(networkState)
        }
    }

    private fun hasExtraRow() = networkState.status != NetworkState.Status.SUCCESS

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) R.layout.network_state_item else R.layout.article_row
    }
}
