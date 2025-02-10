package com.example.newsapploginpage.model

data class AdminArticles(
    val title: String,
    val category: String = "",
    val timeAgo: String = "",
    val source: String = "",
    val imageUrl: String
)

