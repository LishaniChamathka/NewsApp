package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapploginpage.R
import com.example.newsapploginpage.model.Recommendation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RecommendationAdapter(private val onItemClick: (Recommendation) -> Unit) :
    RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    private var recommendations: List<Recommendation> = emptyList()

    fun submitList(list: List<Recommendation>) {
        recommendations = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recommendation, parent, false)
        return RecommendationViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val recommendation = recommendations[position]
        holder.bind(recommendation, onItemClick)
    }

    override fun getItemCount() = recommendations.size

    class RecommendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val category: TextView = itemView.findViewById(R.id.tvCategory)
        private val timeAgo: TextView = itemView.findViewById(R.id.tvTimeAgo)
        private val source: TextView = itemView.findViewById(R.id.tvSource)
        private val image: ImageView = itemView.findViewById(R.id.imgRecommendation)

        fun bind(recommendation: Recommendation, onItemClick: (Recommendation) -> Unit) {
            title.text = recommendation.title
            category.text = recommendation.category
            timeAgo.text = recommendation.timeAgo
            source.text = recommendation.source
            image.setImageResource(recommendation.imageResId)
            itemView.setOnClickListener { onItemClick(recommendation) }
        }
    }


}
