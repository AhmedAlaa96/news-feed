package com.example.newsfeeds.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsfeeds.data.models.Article
import com.example.newsfeeds.domain.GetArticlesUseCase
import com.example.newsfeeds.presentation.viewModels.base.BaseViewModel

class GetArticlesViewModel(private val getArticlesUseCase: GetArticlesUseCase): BaseViewModel() {

    private val _getArticles: MutableLiveData<List<*>> by lazy { MutableLiveData<List<*>>() }

    val getArticles: LiveData<List<*>> = _getArticles


    val isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    init {
        isLoading.value = false
    }

    fun loading(isLoading:Boolean){
        this.isLoading.value = isLoading
    }

    fun getArticlesList(){
       getArticlesUseCase.execute({
           _getArticles.value  = listOf(true,it)
       },{
           _getArticles.value = listOf(false,it.message)
       })
    }

    fun dispose(){
        getArticlesUseCase.dispose()
    }


}