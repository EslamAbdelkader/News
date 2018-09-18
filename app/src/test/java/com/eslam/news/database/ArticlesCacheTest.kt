package com.eslam.news.database

import android.arch.lifecycle.LiveData
import com.eslam.news.model.Article
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executor

class ArticlesCacheTest {

    private val dao = mock<ArticleDao>()
    private val database = mock<NewsDatabase>()
    private val executor = CurrentThreadExecutor()
    private val cache = ArticlesCache(database, executor)

    private val liveData = mock<LiveData<List<Article>>>()

    @Before
    fun init() {
        whenever(database.articlesDao()).thenReturn(dao)
        whenever(dao.getArticles()).thenReturn(liveData)
    }

    @Test
    fun updateArticle() {
        val article = Article("Article", "", "", "", true)
        doNothing().whenever(dao).insert(article)
        doNothing().whenever(dao).delete(article)
        cache.updateArticle(article)
        verify(dao, times(1)).insert(article)
        article.favorite = false
        cache.updateArticle(article)
        verify(dao, times(1)).delete(article)

    }

    @Test
    fun getFavoriteArticles() {
        assertEquals(cache.getFavoriteArticles(), liveData)
    }
}

class CurrentThreadExecutor : Executor {
    override fun execute(command: Runnable?) {
        command?.run()
    }
}
