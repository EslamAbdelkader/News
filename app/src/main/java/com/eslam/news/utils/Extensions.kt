package com.eslam.news.utils

import android.support.v7.util.DiffUtil
import com.eslam.news.model.Article
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


object ArticleComparator : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.title == newItem.title && oldItem.favorite == newItem.favorite
}

fun <T> Observable<T>.ioSubscribe() =
    subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
