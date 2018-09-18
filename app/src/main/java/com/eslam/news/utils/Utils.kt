package com.eslam.news.utils

import android.support.v7.util.DiffUtil
import com.eslam.news.model.Article
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

// Used for comparing lists in ListAdapters
object ArticleComparator : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.title == newItem.title && oldItem.favorite == newItem.favorite
}

/**
 * Subscribes and observes on IO Thread
 */
fun <T> Observable<T>.ioSubscribe() =
        subscribeOn(Schedulers.io()).observeOn(Schedulers.io())

/**
 * Compares two dates
 */
fun isSameDate(date1: Date, date2: Date): Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance()
    cal1.time = date1
    cal2.time = date2
    return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
            && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
            && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
}