package com.example.newsapploginpage

import android.annotation.SuppressLint
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
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  inflater.inflate(R.layout.fragment_accept, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewAccept)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val articlesList = listOf(
            AdminArticles(
                adminTitle = "Grad 5 scholarship exam results released",
                adminSource = "Ada Derana",
                adminTime = "2 Hours ago",
                adminCategory = "Educational",
                adminImageUrl = R.drawable.sport
            ),
            AdminArticles(
                adminTitle = "Tech Innovations 2025",
                adminSource = "TechCrunch",
                adminTime = "5 Hours ago",
                adminCategory = "Technology",
                adminImageUrl = R.drawable.sport
            )
        )

        recyclerView.adapter = AdminFragmentArticleAdapter(articlesList)

        return view

    }
}
