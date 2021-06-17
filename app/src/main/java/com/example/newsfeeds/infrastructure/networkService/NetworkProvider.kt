package com.example.newsfeeds.infrastructure.networkService

import android.telecom.Call
import com.example.newsfeeds.data.models.Article
import com.example.newsfeeds.data.models.ResponseModel
import io.reactivex.Single
import retrofit2.Callback
import retrofit2.Response

object NetworkProvider {

    fun getArticleData(queryParameters: Map<String,String>): Single<List<Article>>{
        return Single.create {
            val call = RetrofitClient.apiService().getArticles(queryParameters)
            call.enqueue(object : Callback<ResponseModel> {
                override fun onResponse(
                    call: retrofit2.Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    if (response.isSuccessful) {
                        if(response.body() != null){
                            it.onSuccess(response.body()!!.articles)
                        }else{
                            it.onError(Throwable("Body is null"))
                        }
                    }else{
                        it.onError(Throwable("response is not successful: ${response.code()}"))
                    }
                }

                override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                    it.onError(t)
                }

            })
        }
    }
}