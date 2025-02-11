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


class AdminFragmentArticleAdapter(private val articles: List<AdminArticles>) :
    RecyclerView.Adapter<AdminFragmentArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val articleImage: ImageView = view.findViewById(R.id.articleAdminFragmentImage)
        val articleTitle: TextView = view.findViewById(R.id.articleAdminFragmentTitle)
        val articleSource: TextView = view.findViewById(R.id.articleAdminFragmentSource)
        val articleTime: TextView = view.findViewById(R.id.articleAdminFragmentTime)
        val articleCategory: TextView = view.findViewById(R.id.articleAdminFragmentCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.articleCategory.text = article.adminCategory
        holder.articleTime.text = article.adminTime
        holder.articleTitle.text = article.adminTitle
        holder.articleSource.text = article.adminSource

        Glide.with(holder.itemView.context)
            .load(article.adminImageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.articleImage)
    }

    // Returns the total number of items in the list
    override fun getItemCount(): Int = articles.size
}
