package com.eslam.news.ui

import android.arch.paging.PagedListAdapter
import android.graphics.Color
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eslam.news.R
import com.eslam.news.model.Article
import com.eslam.news.model.NetworkState

class ArticlesAdapter : PagedListAdapter<Article, RecyclerView.ViewHolder>(ARTICLE_COMPARATOR) {
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
            if(item?.favorite == true){
                holder.title.setTextColor(Color.RED)
            }else{
                holder.title.setTextColor(Color.BLACK)
            }
            holder.title.text = item?.title
        }
        else
            (holder as NetworkViewHolder).progressBar.visibility = View.VISIBLE
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

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                    oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                    oldItem == newItem
        }
    }
}