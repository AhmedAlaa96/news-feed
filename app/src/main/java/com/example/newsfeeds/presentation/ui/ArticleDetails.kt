package com.example.newsfeeds.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsfeeds.R
import com.example.newsfeeds.data.models.Article
import com.example.newsfeeds.databinding.ActivityArticleDetailsBinding
import com.squareup.picasso.Picasso


class ArticleDetails : AppCompatActivity() {
    private lateinit var article: Article
    private lateinit var binding: ActivityArticleDetailsBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.app_name)
        article = intent.getSerializableExtra("article") as Article
        initComponents()
    }

    private fun initComponents() {

        if(article.urlToImage.isEmpty()){
            Picasso
                .get()
                .load(R.drawable.placeholder)
                .into(binding.articleImage)
        }else {
            Picasso
                .get()
                .load(article.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(binding.articleImage)
        }
        binding.titleTxt.text = article.title
        binding.publishedByTxt.text = "By ${article.author}"
        binding.publishedAtTxt.text = article.toDate()
        binding.articleDescription.text = Html.fromHtml(article.description).toString()


        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.openUrl.setOnClickListener {
            if(article.url.isNotEmpty()) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(browserIntent)
            }else{
                Toast.makeText(applicationContext,"Invalid URL",Toast.LENGTH_SHORT).show()
            }
        }
    }
}