package com.example.newsapploginpage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapploginpage.model.NewsItem
import com.example.newsapploginpage.R

class BreakingNewsAdapter(
    private var newsList: List<NewsItem> = listOf(),
    private val onItemClick: (NewsItem) -> Unit
) : RecyclerView.Adapter<BreakingNewsAdapter.BreakingNewsViewHolder>() {

    inner class BreakingNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgBreakingNews: ImageView = itemView.findViewById(R.id.imgBreakingNews)
        val tvBreakingTitle: TextView = itemView.findViewById(R.id.tvBreakingTitle)

        fun bind(news: NewsItem) {
            imgBreakingNews.setImageResource(news.imageRes)
            tvBreakingTitle.text = news.title

            itemView.setOnClickListener {
                onItemClick(news)
            }
        }
    }

    fun updateList(newList: List<NewsItem>) {
        newsList = newList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreakingNewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.breaking_news_item, parent, false)
        return BreakingNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: BreakingNewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size
}