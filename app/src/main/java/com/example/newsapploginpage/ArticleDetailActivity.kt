package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ArticleDetailActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_article_detail_layout)

        val articleTitle = intent.getStringExtra("article_title") ?: "No Title"
        val articleDescription = intent.getStringExtra("article_description") ?: "No Description"
        val articleImage = intent.getStringExtra("article_image") // Could be a URL or resource ID

        val titleTextView: TextView = findViewById(R.id.articleTitle)
        val descTextView: TextView = findViewById(R.id.articleContent)
        val imageView: ImageView = findViewById(R.id.articleImage)

        titleTextView.text = articleTitle
        descTextView.text = articleDescription

        if (!articleImage.isNullOrEmpty()) {
            if (articleImage.startsWith("http")) {
                // Load image from URL
                Glide.with(this)
                    .load(articleImage)
                    .placeholder(R.drawable.sport) // Placeholder while loading
                    .error(R.drawable.error_image) // Show on error
                    .into(imageView)
            } else {
                // Try parsing as resource ID
                val imageResId = articleImage.toIntOrNull()
                if (imageResId != null) {
                    imageView.setImageResource(imageResId)
                } else {
                    imageView.setImageResource(R.drawable.sport) // Default fallback
                }
            }
        } else {
            imageView.setImageResource(R.drawable.sport) // Default fallback if image is null
        }
    }
}
