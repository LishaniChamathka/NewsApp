package com.example.newsapploginpage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsapploginpage.R
import com.example.newsapploginpage.model.Channel

class ChannelAdapter(private val onItemClick: (Channel) -> Unit) :
    RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    private var channels: List<Channel> = emptyList()

    fun submitList(list: List<Channel>) {
        channels = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channel = channels[position]
        holder.bind(channel, onItemClick)
    }

    override fun getItemCount() = channels.size

    class ChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val channelLogo: ImageView = itemView.findViewById(R.id.ivChannelLogo)
        private val channelName: TextView = itemView.findViewById(R.id.tvChannelName)

        fun bind(channel: Channel, onItemClick: (Channel) -> Unit) {
            Glide.with(itemView.context)
                .load(channel.logoRes)  // If using a URL, replace with recommendation.imageUrl
                .apply(RequestOptions.circleCropTransform()) // 🔹 Make Image Circular
                .into(channelLogo)

            channelName.text = channel.name
            itemView.setOnClickListener { onItemClick(channel) }
        }


    }
}
