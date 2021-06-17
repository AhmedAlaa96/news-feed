package com.example.newsfeeds.domain

import com.example.newsfeeds.domain.base.SingleUseCase

import com.example.newsfeeds.data.models.Article
import com.example.newsfeeds.domain.repository.ArticlesRepository
import io.reactivex.Single

class GetArticlesUseCase constructor(private val repository: ArticlesRepository): SingleUseCase<List<Article>>() {
    override fun run(): Single<List<Article>> = repository.getArticles()

}