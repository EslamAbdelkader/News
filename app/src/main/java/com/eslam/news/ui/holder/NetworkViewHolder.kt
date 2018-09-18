package com.eslam.news.ui.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.eslam.news.R
import com.eslam.news.model.NetworkState

class NetworkViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val errorMsg = view.findViewById<TextView>(R.id.error_msg)
    private val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)

    fun bind(networkState: NetworkState){
        if (networkState.status == NetworkState.Status.LOADING){
            progressBar.visibility = View.VISIBLE
            errorMsg.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            errorMsg.visibility = View.VISIBLE
            errorMsg.text = view.context.getString(R.string.error_msg)
        }
    }
}
