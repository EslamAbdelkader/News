package com.eslam.news.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.eslam.news.R

class NetworkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val errorMsg = view.findViewById<TextView>(R.id.error_msg)
    val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
    val retryButton = view.findViewById<Button>(R.id.retry_button)
}
