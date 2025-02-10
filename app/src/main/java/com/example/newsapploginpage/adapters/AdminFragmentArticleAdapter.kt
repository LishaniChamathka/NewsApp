package com.example.newsapploginpage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapploginpage.R
import com.example.newsapploginpage.model.AdminArticles

class AdminFragmentArticleAdapter(private var articles: List<AdminArticles>) :
    RecyclerView.Adapter<AdminFragmentArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_acceptance_article_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.categoryText.text = article.category
        holder.timeText.text = article.timeAgo
        holder.newsTitle.text = article.title
        holder.sourceText.text = article.source

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(article.imageUrl)
            .placeholder(R.drawable.today1) // Default image
            .into(holder.imgTodayNews)
    }

    override fun getItemCount(): Int = articles.size

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgTodayNews: ImageView = itemView.findViewById(R.id.imgTodayNews)
        val categoryText: TextView = itemView.findViewById(R.id.categoryText)
        val timeText: TextView = itemView.findViewById(R.id.timeText)
        val newsTitle: TextView = itemView.findViewById(R.id.newsTitle)
        val sourceText: TextView = itemView.findViewById(R.id.sourceText)
    }
}
