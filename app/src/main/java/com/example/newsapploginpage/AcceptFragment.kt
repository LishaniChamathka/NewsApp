package com.example.newsapploginpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapploginpage.adapters.AdminFragmentArticleAdapter
import com.example.newsapploginpage.model.AdminArticles

class AcceptFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: AdminFragmentArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_accept, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewAccept)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val acceptedArticles = listOf(
            AdminArticles(
                title = "Grade 5 Results Out",
                category = "Educational",
                timeAgo = "2 Hours ago",
                source = "Ada Derana",
                imageUrl = "android.resource://${requireContext().packageName}/${R.drawable.today1}"
            ),
            AdminArticles(
                title = "Tech Innovations 2025",
                category = "Technology",
                timeAgo = "5 Hours ago",
                source = "TechCrunch",
                imageUrl = "android.resource://${requireContext().packageName}/${R.drawable.today1}"
            )
        )

        articleAdapter = AdminFragmentArticleAdapter(acceptedArticles)
        recyclerView.adapter = articleAdapter
    }
}
