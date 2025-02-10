package com.example.newsapploginpage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapploginpage.R
import com.example.newsapploginpage.model.Article

class ArticleAdapter(
    private val context: Context,
    private val articleList: List<Article>,
    private val onAcceptClick: (Article) -> Unit,
    private val onDeleteClick: (Article) -> Unit,
    private val onAcceptOkClick: (Article) -> Unit,
    private val onRejectOkClick: (Article) -> Unit,
    private val onItemClick: (Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val imgTodayNews: ImageView
        val articleImage: ImageView = view.findViewById(R.id.adminArticleImage)
        val articleTitle: TextView = view.findViewById(R.id.adminArticleTitle)
        val articleDescription: TextView = view.findViewById(R.id.adminArticleDescription)
        val btnAccept: Button = view.findViewById(R.id.adminApproveButton)
        val btnReject: Button = view.findViewById(R.id.adminRejectButton)
        val btnAcceptOk: Button = view.findViewById(R.id.admin_ok_button)
        val btnRejectOk: Button = view.findViewById(R.id.reject_ok_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_admin_article_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]

        holder.articleTitle.text = article.title
        holder.articleDescription.text = article.description

        // Load image safely using Glide
        Glide.with(context)
            .load(article.imageUrl) // Ensure it's a valid URL
            .placeholder(R.drawable.sport) // Add placeholder
            .into(holder.articleImage)


        // Handle button clicks
        holder.btnAccept.setOnClickListener { onAcceptClick(article) }
        holder.btnReject.setOnClickListener { onDeleteClick(article) }
        holder.btnAcceptOk.setOnClickListener { onAcceptOkClick(article) }
        holder.btnRejectOk.setOnClickListener { onRejectOkClick(article) }

        // Handle item click
        holder.itemView.setOnClickListener { onItemClick(article) }
    }

    override fun getItemCount(): Int = articleList.size
}
