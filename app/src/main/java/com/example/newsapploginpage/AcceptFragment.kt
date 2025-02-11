package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapploginpage.adapters.AdminFragmentArticleAdapter
import com.example.newsapploginpage.model.AdminArticles
import com.google.firebase.firestore.FirebaseFirestore

class AcceptFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: AdminFragmentArticleAdapter
    private val articlesList = mutableListOf<AdminArticles>()
    private lateinit var db: FirebaseFirestore

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  inflater.inflate(R.layout.fragment_accept, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewAccept)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        db = FirebaseFirestore.getInstance()
        articleAdapter = AdminFragmentArticleAdapter(articlesList)
        recyclerView.adapter = articleAdapter

        fetchPendingArticles()

        return view

    }

    private fun changetimeToAgo(time: String): String {
        val currentTime = System.currentTimeMillis()
        val articleTime = time.toLong()
        val diff = currentTime - articleTime
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = weeks / 4
        val years = months / 12

        return when {
            years > 0 -> "$years years ago"
            months > 0 -> "$months months ago"
            weeks > 0 -> "$weeks weeks ago"
            days > 0 -> "$days days ago"
            hours > 0 -> "$hours hours ago"
            minutes > 0 -> "$minutes minutes ago"
            else -> "$seconds seconds ago"
        }
    }


    private fun fetchPendingArticles() {
        db.collection("articles")
            .whereEqualTo("status", "approved")
            .get()
            .addOnSuccessListener { documents ->
                articlesList.clear()
                for (document in documents) {
                    val fullContent = document.getString("content") ?: ""
                    val shortContent = if (fullContent.length > 24) {
                        fullContent.take(24) + "..."
                    } else {
                        fullContent
                    }
                    val article = AdminArticles(
                        adminId = document.id,
                        adminTitle = document.getString("title")!!,
                        adminSource = document.getString("author")!!,
                        adminTime = changetimeToAgo(document.getString("date")!!),
                        adminCategory = document.getString("category")!!,
                        adminImageUrl = document.getString("imageUrl")!!,
                        adminContent = shortContent,
                        fullContent = fullContent

                    )
                    articlesList.add(article)
                }
                articleAdapter.notifyDataSetChanged() // Refresh RecyclerView
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to load articles: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}
