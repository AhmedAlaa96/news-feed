package com.example.newsfeeds.domain.repository

import com.example.newsfeeds.data.models.Article
import io.reactivex.Single

interface ArticlesRepository {
    fun getArticles(): Single<List<Article>>
}