package com.eslam.news.utils

import com.eslam.news.model.Article
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleComparatorTest {

    private val article = Article("Article","","","",true)
    private val articleModified = article.copy(favorite = false)
    private val anotherArticle = Article("Another Article","","","",true)

    @Test
    fun areItemsTheSame() {
        assertEquals(ArticleComparator.areItemsTheSame(article,articleModified),true)
        assertEquals(ArticleComparator.areItemsTheSame(article,anotherArticle),false)
    }

    @Test
    fun areContentsTheSame() {
        assertEquals(ArticleComparator.areContentsTheSame(article,articleModified),false)
        assertEquals(ArticleComparator.areContentsTheSame(article,anotherArticle),false)
    }
}