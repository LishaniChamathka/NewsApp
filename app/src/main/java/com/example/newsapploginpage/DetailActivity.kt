package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val title = intent.getStringExtra("news_title")
        val category = intent.getStringExtra("news_category")
        val imageRes = intent.getIntExtra("news_image", 0)
        val detail = intent.getStringExtra("news_in_detail")

        val imgNews: ImageView = findViewById(R.id.imgNewsDetail)
        val tvTitle: TextView = findViewById(R.id.tvNewsTitleDetail)
        val tvCategory: TextView = findViewById(R.id.tvNewsCategoryDetail)
        val newsDetail: TextView = findViewById(R.id.tvNewsInDetail)

        tvTitle.text = title
        tvCategory.text = category
        imgNews.setImageResource(imageRes)
        newsDetail.text = detail
    }
}
