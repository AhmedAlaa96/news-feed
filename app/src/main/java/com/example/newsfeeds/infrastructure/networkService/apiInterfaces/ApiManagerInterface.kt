package com.example.newsfeeds.infrastructure.networkService.apiInterfaces

import com.example.newsfeeds.data.models.Article
import com.example.newsfeeds.data.models.ResponseModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*


interface ApiManagerInterface {


    @GET("articles")
    fun getArticles(@QueryMap(encoded = true) queryParameters: Map<String,String>) :  Call<ResponseModel>


}