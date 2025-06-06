package com.example.newsapploginpage.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapploginpage.model.NewsItem
import com.example.newsapploginpage.R

class TodayNewsAdapter(
    private var newsList: List<NewsItem> = listOf(),
    private val onItemClick: (NewsItem) -> Unit
) : RecyclerView.Adapter<TodayNewsAdapter.TodayNewsViewHolder>() {

    inner class TodayNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgTodayNews: ImageView = itemView.findViewById(R.id.imgTodayNews)
        private val tvTodayTitle: TextView = itemView.findViewById(R.id.newsTitle)
        private val tvTodayCategory: TextView = itemView.findViewById(R.id.categoryText)
        private val tvTimeText: TextView = itemView.findViewById(R.id.timeText)
        private val tvSourceText: TextView = itemView.findViewById(R.id.sourceText)

        fun bind(news: NewsItem) {
            imgTodayNews.setImageResource(news.imageRes)
            tvTodayTitle.text = news.title
            tvTodayCategory.text = news.category
            tvTimeText.text = news.time
            tvSourceText.text = news.source

            itemView.setOnClickListener {
                onItemClick(news)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<NewsItem>) {
        newsList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayNewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.today_news_item, parent, false)
        return TodayNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodayNewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size
}