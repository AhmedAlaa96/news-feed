package com.example.newsfeeds.data.models

data class ResponseModel(val status: String,
                         val source: String,
                         val sortBy: String,
                         val articles: List<Article>
)