package com.eslam.news.database

import com.eslam.news.model.Article
import java.util.concurrent.Executor

open class ArticlesCache(private val database: NewsDatabase, private val executor: Executor) {
    open fun updateArticle(article: Article) {
        executor.execute {
            if (article.favorite == true)
                database.articlesDao().insert(article)
            else
                database.articlesDao().delete(article)
        }
    }

    // Gets favorite articles from database
    open fun getFavoriteArticles() = database.articlesDao().getArticles()
}