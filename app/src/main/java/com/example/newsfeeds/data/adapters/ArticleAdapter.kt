package com.example.newsfeeds.data.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeeds.presentation.ui.ArticleDetails
import com.example.newsfeeds.R
import com.example.newsfeeds.data.models.Article
import com.example.newsfeeds.databinding.ArticleItemBinding
import com.squareup.picasso.Picasso

class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {


    var items: List<Article> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)

        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(private val view:ArticleItemBinding): RecyclerView.ViewHolder(view.root), View.OnClickListener{
        private lateinit var article: Article
        init {
            view.root.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bindView(article: Article){
            this.article = article

            view.titleTxt.text = article.title
            view.publishedByTxt.text = "By ${article.author}"
            view.publishedAtTxt.text = article.toDate()


            Picasso
                .get()
                .load(article.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(view.articleImage)

        }

        override fun onClick(v: View?) {
            val intent = Intent(view.root.context, ArticleDetails::class.java)
            intent.putExtra("article",this.article)

            view.root.context.startActivity(intent)
        }
    }

}