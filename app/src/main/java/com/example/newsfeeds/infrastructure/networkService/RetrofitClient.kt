package com.example.newsfeeds.infrastructure.networkService

import com.example.newsfeeds.infrastructure.networkService.apiInterfaces.ApiManagerInterface
import com.example.newsfeeds.infrastructure.utils.Constants
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val logging =  HttpLoggingInterceptor()
    // set your desired log level
    private val okHttpClient =  OkHttpClient.Builder()

    init {
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.interceptors().add(logging)
    }


    companion object{

        private val retrofitClient = RetrofitClient()

        private fun retrofitBuilder(): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(retrofitClient.okHttpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
        }

        fun apiService(): ApiManagerInterface{
           return retrofitBuilder()
                .build()
                .create(ApiManagerInterface::class.java)
        }
    }


}
