package com.example.newsfeeds.data.models

import android.annotation.SuppressLint
import java.io.Serializable
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")

data class Article(
    val title: String,
    val author: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
): Serializable  {
    @SuppressLint("SimpleDateFormat")
    fun toDate(): String {
        if(publishedAt.isEmpty()){
            return ""
        }
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        try {
            val date: Date = format.parse(publishedAt)
            return "${getMonth(date.month)} ${date.day}, ${publishedAt.substring(0,4)}"
        } catch (e: ParseException) {
            e.printStackTrace()
        }
       return publishedAt
    }

    private fun getMonth(month: Int): String? {
        return DateFormatSymbols().months[month - 1]
    }

    override fun toString(): String {
        return "Article(title='$title', author='$author', description='$description', url='$url', urlToImage='$urlToImage', publishedAt='$publishedAt')"
    }


}