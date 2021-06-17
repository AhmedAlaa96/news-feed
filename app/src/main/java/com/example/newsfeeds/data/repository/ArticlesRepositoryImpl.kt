package com.example.newsfeeds.data.repository

import com.example.newsfeeds.data.models.Article
import com.example.newsfeeds.domain.repository.ArticlesRepository
import com.example.newsfeeds.infrastructure.networkService.NetworkProvider
import io.reactivex.Single

class ArticlesRepositoryImpl: ArticlesRepository {
    override fun getArticles(): Single<List<Article>> {
        val queryParameters1 = mapOf(
            "source" to "the-next-web",
            "apiKey" to "533af958594143758318137469b41ba9"
        )
        val queryParameters2 = mapOf(
            "source" to "associated-press",
            "apiKey" to "533af958594143758318137469b41ba9"
        )

        return Single.create {
            try {
                val responseModel1 = NetworkProvider.getArticleData(queryParameters1).blockingGet()
                val responseModel2 = NetworkProvider.getArticleData(queryParameters2).blockingGet()
                val result = mutableListOf<Article>()
                if(responseModel1.isNotEmpty()) {
                    result.addAll(responseModel1)
                }
                if(responseModel2.isNotEmpty()) {
                    result.addAll(responseModel2)
                }
                it.onSuccess(result)
            }catch (error: Exception){
                it.onError(Throwable(error.message))
            }

        }


    }
}