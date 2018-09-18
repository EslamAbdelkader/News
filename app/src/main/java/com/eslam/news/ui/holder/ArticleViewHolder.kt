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
import com.eslam.news.utils.isSameDate
import java.text.SimpleDateFormat
import java.util.*


class ArticleViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val title = view.findViewById<TextView>(R.id.title)
    val date = view.findViewById<TextView>(R.id.date)
    val dateSeparator = view.findViewById<View>(R.id.dateSeparator)
    val image = view.findViewById<ImageView>(R.id.image)
    val star = view.findViewById<ImageView>(R.id.star)
    val separator = view.findViewById<View>(R.id.separator)
    val itemDate = view.findViewById<TextView>(R.id.itemDate)
    val mainContainer = view.findViewById<View>(R.id.mainContainer)

    fun bind(item: Article, position: Int, previousItem: Article?) {
        title.text = item.title
        itemDate.text = formatDate(item.getDate())
        date.text = formatDate(item.getDate())
        if (item.favorite == true)
            star.setImageResource(R.drawable.starred)
        else
            star.setImageResource(R.drawable.star)
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.news_placeholder)
        requestOptions.error(R.drawable.news_placeholder)
        Glide.with(view).load(item.urlToImage).apply(requestOptions).into(image)
        handleGrouping(item, position, previousItem)
        mainContainer.setOnClickListener { view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url))) }
    }

    private fun handleGrouping(item: Article, position: Int, previousItem: Article?) {
        if (position == 0 || !isSameDate(item.getDate(), previousItem?.getDate())) {
            date.visibility = View.VISIBLE
            if (position != 0)
                dateSeparator.visibility = View.VISIBLE
            else
                dateSeparator.visibility = View.GONE
        } else {
            date.visibility = View.GONE
        }
    }

    fun formatDate(date: Date): String? {
        return SimpleDateFormat("E dd-MMM-yyyy").format(date)
    }
}
