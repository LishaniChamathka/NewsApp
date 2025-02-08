package com.example.newsapploginpage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapters.RecommendationAdapter
import com.example.newsapploginpage.adapters.ChannelAdapter
import com.example.newsapploginpage.adapters.TopicAdapter
import com.example.newsapploginpage.model.Channel
import com.example.newsapploginpage.model.Recommendation
import com.example.newsapploginpage.model.Topic
import com.example.newsapploginpage.databinding.ActivityExploreBinding

class ExploreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExploreBinding
    private lateinit var topicAdapter: TopicAdapter
    private lateinit var channelAdapter: ChannelAdapter
    private lateinit var recommendationAdapter: RecommendationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        loadData()
    }

    private fun setupRecyclerViews() {
        topicAdapter = TopicAdapter { topic -> showToast("Clicked: ${topic.name}") }
        channelAdapter = ChannelAdapter { channel -> showToast("Clicked: ${channel.name}") }
        recommendationAdapter = RecommendationAdapter { recommendation -> showToast("Clicked: ${recommendation.title}") }

        binding.rvTopics.apply {
            layoutManager = LinearLayoutManager(this@ExploreActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = topicAdapter
        }

        binding.rvChannels.apply {
            layoutManager = LinearLayoutManager(this@ExploreActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = channelAdapter
        }

        binding.rvRecommendations.apply {
            layoutManager = LinearLayoutManager(this@ExploreActivity)
            adapter = recommendationAdapter
        }
    }

    private fun loadData() {
        val topics = listOf(
            Topic("Sport", R.drawable.sport),
            Topic("Politics", R.drawable.politics),
            Topic("Health", R.drawable.health),
            Topic("Education", R.drawable.today1),
            Topic("Sport", R.drawable.sport),
            Topic("Politics", R.drawable.politics),
            Topic("Health", R.drawable.health),
            Topic("Education", R.drawable.today1)

        )

        val channels = listOf(
            Channel("LankaDeepa", R.drawable.lankadeepa),
            Channel("Diwaina", R.drawable.diwaina),
            Channel("CNN", R.drawable.cnn),
            Channel("News First", R.drawable.newsfist),
            Channel("LankaDeepa", R.drawable.lankadeepa),
            Channel("Diwaina", R.drawable.diwaina),
            Channel("CNN", R.drawable.cnn),
            Channel("News First", R.drawable.newsfist)
        )

        val recommendations = listOf(
            Recommendation("T20 world up win goes to the Indian cricket team", "Sports", "2 Hours ago", "LankaDeepa", R.drawable.sport),
            Recommendation("Election updates", "Politics", "1 Day ago", "CNN", R.drawable.politics),
            Recommendation("COVID-19 updates", "Health", "5 Hours ago", "News Fist", R.drawable.health),
            Recommendation("T20 world up win goes to the Indian cricket team", "Sports", "2 Hours ago", "LankaDeepa", R.drawable.sport),
            Recommendation("Election updates", "Politics", "1 Day ago", "CNN", R.drawable.politics),
            Recommendation("COVID-19 updates", "Health", "5 Hours ago", "News Fist", R.drawable.health)
        )

        topicAdapter.submitList(topics)
        channelAdapter.submitList(channels)
        recommendationAdapter.submitList(recommendations)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
