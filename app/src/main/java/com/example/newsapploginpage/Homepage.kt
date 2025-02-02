package com.example.newsapploginpage

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapploginpage.adapters.BreakingNewsAdapter
import com.example.newsapploginpage.adapters.TodayNewsAdapter
import com.example.newsapploginpage.model.NewsItem

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        // Sample data
        val breakingNewsList = listOf(
            NewsItem("Olympic women's badminton championship went to India", "Sports", R.drawable.breaking1, "Detailed description about the Olympic women's badminton championship..."),
            NewsItem("Sri Lanka faces major floods", "Weather", R.drawable.breaking2, "Detailed description about the major floods in Sri Lanka...")
        )

        val todayNewsList = listOf(
            NewsItem("Grade 5 scholarship exam results released", "Educational", R.drawable.today1, "Detailed description about the Grade 5 scholarship exam results..."),
            NewsItem("Sri Lankan president departs for China visit", "Politics", R.drawable.today2, "Detailed description about the Sri Lankan president's visit to China...")
        )

        // Setting up Breaking News RecyclerView (Horizontal)
        val recyclerBreakingNews: RecyclerView = findViewById(R.id.recyclerBreakingNews)
        recyclerBreakingNews.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerBreakingNews.adapter = BreakingNewsAdapter(breakingNewsList) { selectedNews ->
            openDetailPage(selectedNews)
        }

        // Setting up Today News RecyclerView (Vertical)
        val recyclerTodayNews: RecyclerView = findViewById(R.id.recyclerTodayNews)
        recyclerTodayNews.layoutManager = LinearLayoutManager(this)
        recyclerTodayNews.adapter = TodayNewsAdapter(todayNewsList) { selectedNews ->
            openDetailPage(selectedNews)
        }

        // View All Button
        val viewAllTodayNews = findViewById<TextView>(R.id.tvViewAllTodayNews)
//        viewAllTodayNews.setOnClickListener {
//            val intent = Intent(this, AllNewsActivity::class.java)
//            startActivity(intent)
//        }
    }

    // Function to open the detailed page
    private fun openDetailPage(news: NewsItem) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("news_title", news.title)
            putExtra("news_category", news.category)
            putExtra("news_image", news.imageRes)
            putExtra("news_in_detail", news.detail)
        }
        startActivity(intent)
    }
}
