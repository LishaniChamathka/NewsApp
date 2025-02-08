package com.example.newsapploginpage.model

data class Recommendation(
    val title: String,
    val category: String,
    val timeAgo: String,
    val source: String,
    val imageResId: Int
)