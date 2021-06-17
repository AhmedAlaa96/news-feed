package com.example.newsfeeds.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeeds.R
import com.example.newsfeeds.data.adapters.ArticleAdapter
import com.example.newsfeeds.data.adapters.DrawerAdapter
import com.example.newsfeeds.data.models.Article
import com.example.newsfeeds.data.models.DrawerItem
import com.example.newsfeeds.data.repository.ArticlesRepositoryImpl
import com.example.newsfeeds.databinding.ActivityMainBinding
import com.example.newsfeeds.domain.GetArticlesUseCase
import com.example.newsfeeds.presentation.viewModels.GetArticlesViewModel
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var drawerAdapter: DrawerAdapter
    private lateinit var getArticleViewModel: GetArticlesViewModel
    private lateinit var articleList : RecyclerView
    private lateinit var drawerList : ListView
    private lateinit var emptyState : View
    private lateinit var retryBtn : MaterialButton
    private lateinit var loadingArticles : View
    private lateinit var drawerLayout: DrawerLayout

        @SuppressLint("RestrictedApi", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawerLayout = binding.drawerLayout
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        articleList = drawerLayout.findViewById<View>(R.id.contentMain).findViewById(R.id.articleListView)
        drawerList = binding.drawerItems
        emptyState = drawerLayout.findViewById<View>(R.id.contentMain).findViewById(R.id.emptyState)
        retryBtn = drawerLayout.findViewById<View>(R.id.contentMain).findViewById(R.id.retryBtn)
        loadingArticles =  drawerLayout.findViewById<View>(R.id.contentMain).findViewById(R.id.loadingArticles)


        initComponents()

    }

    private fun initComponents() {

//        val navView: NavigationView = binding.navView
//
//        navView.setNavigationItemSelectedListener {
//            Toast.makeText(applicationContext,it.title,Toast.LENGTH_SHORT).show()
//            drawerLayout.closeDrawer(GravityCompat.START)
//            return@setNavigationItemSelectedListener true
//        }

        drawerAdapter = DrawerAdapter(listOf(
            DrawerItem(getString(R.string.menu_explore),R.drawable.explore,true),
            DrawerItem(getString(R.string.menu_live_chat),R.drawable.live,false),
            DrawerItem(getString(R.string.menu_gallery),R.drawable.gallery,false),
            DrawerItem(getString(R.string.menu_wish_list),R.drawable.wishlist,false),
            DrawerItem(getString(R.string.menu_e_magazine),R.drawable.e_magazine,false),
        ))


        drawerList.adapter = drawerAdapter

        drawerList.setOnItemClickListener { _, _, position, _ ->
            drawerAdapter.changeSelected(position)
            Toast.makeText(applicationContext,drawerAdapter.items[position].itemTitle,Toast.LENGTH_SHORT).show()
            drawerLayout.closeDrawer(GravityCompat.START)
        }




        binding.appBarMain.toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)

        getArticleViewModel= GetArticlesViewModel(GetArticlesUseCase(ArticlesRepositoryImpl()))

        articleAdapter = ArticleAdapter()

        getArticleViewModel.getArticles.observe(this,{
            getArticleViewModel.loading(false)
            if(it[0] as Boolean) {
                articleAdapter.items = it[1] as List<Article>
                articleAdapter.notifyDataSetChanged()

                emptyState.visibility = View.GONE
                articleList.visibility = View.VISIBLE
            }else{
                emptyState.visibility = View.VISIBLE
                articleList.visibility = View.GONE
            }
        })

        getArticleViewModel.isLoading.observe(this,{
            runOnUiThread {
                loadingArticles.visibility = if(it) View.VISIBLE else View.GONE
            }
        })






        retryBtn.setOnClickListener {
            emptyState.visibility = View.GONE
            articleList.visibility = View.GONE
            getArticleViewModel.getArticlesList()
            getArticleViewModel.loading(true)

        }

        articleList.adapter = articleAdapter
        articleList.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()

        getArticleViewModel.getArticlesList()
        getArticleViewModel.loading(true)
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        getArticleViewModel.dispose()
        super.onDestroy()
    }

}