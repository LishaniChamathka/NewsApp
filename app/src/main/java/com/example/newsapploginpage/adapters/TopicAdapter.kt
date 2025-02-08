package com.example.newsapploginpage.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsapploginpage.R
import com.example.newsapploginpage.model.Topic

class TopicAdapter(private val onItemClick: (Topic) -> Unit) :
    RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    private var topics: List<Topic> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Topic>) {
        topics = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        return TopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = topics[position]
        holder.bind(topic, onItemClick)
    }

    override fun getItemCount() = topics.size

    class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val topicImage: ImageView = itemView.findViewById(R.id.ivTopic)
        private val topicName: TextView = itemView.findViewById(R.id.tvTopicName)

        fun bind(topic: Topic, onItemClick: (Topic) -> Unit) {
            Glide.with(itemView.context)
                .load(topic.imageRes)  // If using a URL, replace with recommendation.imageUrl
                .apply(RequestOptions.circleCropTransform()) // 🔹 Make Image Circular
                .into(topicImage)

            topicName.text = topic.name
            itemView.setOnClickListener { onItemClick(topic) }
        }
    }
}
