package com.example.newsapploginpage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapploginpage.R
import com.example.newsapploginpage.model.UserArticles

class UserArticleFragmentAdapter(private val articlesList: List<UserArticles>) :
    RecyclerView.Adapter<UserArticleFragmentAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.articleUserFragmentImage)
        val categoryTextView: TextView = itemView.findViewById(R.id.articleUserFragmentCategory)
        val timeTextView: TextView = itemView.findViewById(R.id.articleUserFragmentTime)
        val titleTextView: TextView = itemView.findViewById(R.id.articleUserFragmentTitle)
        val sourceTextView: TextView = itemView.findViewById(R.id.articleUserFragmentSource)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articlesList[position]

        holder.titleTextView.text = article.userTitle
        holder.sourceTextView.text = article.userSource
        holder.timeTextView.text = article.userTime
        holder.categoryTextView.text = article.userCategory

        Glide.with(holder.itemView.context)
            .load(article.userImageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imageView)
    }

    override fun getItemCount() = articlesList.size
}

private fun ImageView.setImageResource(userImageUrl: String) {

}
