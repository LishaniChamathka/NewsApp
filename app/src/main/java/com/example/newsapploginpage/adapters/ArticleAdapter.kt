package com.example.newsapploginpage.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapploginpage.ArticleDetailActivity
import com.example.newsapploginpage.R
import com.example.newsapploginpage.model.Article

class ArticleAdapter(
    private val context: Context,
    private val articleList: List<Article>,
    private val onAcceptClick: (Article) -> Unit,
    private val onItemClick: (Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val articleImage: ImageView = view.findViewById(R.id.adminArticleImage)
        val articleTitle: TextView = view.findViewById(R.id.adminArticleTitle)
        val articleDescription: TextView = view.findViewById(R.id.adminArticleDescription)
        val btnAccept: Button = view.findViewById(R.id.adminApproveButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_admin_article_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]

        holder.articleTitle.text = article.title
        holder.articleDescription.text = article.description
        Glide.with(context).load(article.imageUrl).into(holder.articleImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ArticleDetailActivity::class.java)
            intent.putExtra("article_title", article.title)
            intent.putExtra("article_description", article.description)
            intent.putExtra("article_image", article.imageUrl)
            context.startActivity(intent)
        }

        holder.btnAccept.setOnClickListener {
            onAcceptClick(article)
        }

        holder.itemView.setOnClickListener {
            onItemClick(article)
        }
    }

    override fun getItemCount(): Int = articleList.size
}
