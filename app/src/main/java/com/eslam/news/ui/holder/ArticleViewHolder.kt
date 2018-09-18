package com.eslam.news.ui.holder

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eslam.news.R
import com.eslam.news.model.Article


class ArticleViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val title = view.findViewById<TextView>(R.id.title)
    val image = view.findViewById<ImageView>(R.id.image)
    val star = view.findViewById<ImageView>(R.id.star)

    fun bind(item: Article) {
        title.text = item.title
        if (item.favorite == true)
            star.setImageResource(R.drawable.starred)
        else
            star.setImageResource(R.drawable.star)
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.news_placeholder)
        requestOptions.error(R.drawable.news_placeholder)
        Glide.with(view).load(item.urlToImage).apply(requestOptions).into(image)
        title.setOnClickListener { view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url))) }
    }
}
