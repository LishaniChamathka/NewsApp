package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapploginpage.adapters.UserArticleFragmentAdapter
import com.example.newsapploginpage.model.UserArticles

class SubmitFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  inflater.inflate(R.layout.fragment_submit, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.userRecyclerViewArticles)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val articlesList = listOf(
            UserArticles(
                userTitle = "Grad 5 scholarship exam results released",
                userSource = "Ada Derana",
                userTime = "2 Hours ago",
                userCategory = "Educational",
                userImageUrl = R.drawable.sport
            ),
            UserArticles(
                userTitle = "Tech Innovations 2025",
                userSource = "TechCrunch",
                userTime = "5 Hours ago",
                userCategory = "Technology",
                userImageUrl = R.drawable.sport
            )
        )


        recyclerView.adapter = UserArticleFragmentAdapter(articlesList)

        return view

    }
}
