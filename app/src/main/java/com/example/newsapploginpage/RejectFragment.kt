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


class RejectFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reject, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewReject)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val articlesList = listOf(
            AdminArticles(
                adminId = "1",
                adminTitle = "Grad 5 scholarship exam results released",
                adminSource = "Ada Derana",
                adminTime = "2 Hours ago",
                adminCategory = "Educational",
                adminImageUrl = R.drawable.sport.toString(),
                adminContent = "The results of the Grade 5 Scholarship Examination held in 2021 have been released online, the Department of Examinations said today (October 4).",
                fullContent = "The results of the Grade 5 Scholarship Examination held in 2021 have been released online, the Department of Examinations said today (October 4)."
            ),
            AdminArticles(
                adminId = "2",
                adminTitle = "Tech Innovations 2025",
                adminSource = "TechCrunch",
                adminTime = "5 Hours ago",
                adminCategory = "Technology",
                adminImageUrl = R.drawable.sport.toString(),
                adminContent = "The future of technology is here. With the rise of AI and Machine Learning, the world is changing rapidly. Stay tuned for more updates.",
                fullContent = "The future of technology is here. With the rise of AI and Machine Learning, the world is changing rapidly. Stay tuned for more updates."
            )
        )

// Now pass this list to the adapter
        recyclerView.adapter = AdminFragmentArticleAdapter(articlesList)

        return view
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