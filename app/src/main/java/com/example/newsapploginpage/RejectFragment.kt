package com.example.newsapploginpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapploginpage.adapters.ArticleAdapter
import com.example.newsapploginpage.model.Article

class RejectFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_reject, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        recyclerView = view.findViewById(R.id.recyclerViewReject)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        val rejectedArticles = listOf(
//            Article("Political Unrest", "Politics", "3 Hours ago", "BBC", "android.resource://${requireContext().packageName}/${R.drawable.today1}"),
//            Article("Stock Market Crash", "Finance", "1 Day ago", "Bloomberg", "android.resource://${requireContext().packageName}/${R.drawable.today1}")
//        )
//
//        articleAdapter = ArticleAdapter(rejectedArticles)
//        recyclerView.adapter = articleAdapter
//    }
}