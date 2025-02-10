package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapploginpage.R.*
import com.example.newsapploginpage.adapters.UserArticleFragmentAdapter
import com.example.newsapploginpage.model.UserArticles

class PublishFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        val view = inflater.inflate(layout.fragment_publish, container, false)

        // Find the RecyclerView after inflation
        recyclerView = view.findViewById(R.id.userRecyclerViewArticles)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView in onViewCreated
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val articlesList = listOf(
            UserArticles(
                userTitle = "Grad 5 scholarship exam results released",
                userSource = "Ada Derana",
                userTime = "2 Hours ago",
                userCategory = "Educational",
                userImageUrl = drawable.sport
            ),
            UserArticles(
                userTitle = "Tech Innovations 2025",
                userSource = "TechCrunch",
                userTime = "5 Hours ago",
                userCategory = "Technology",
                userImageUrl = drawable.sport
            )
        )

        recyclerView.adapter = UserArticleFragmentAdapter(articlesList)
    }
}