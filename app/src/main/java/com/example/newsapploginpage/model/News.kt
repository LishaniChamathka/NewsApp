package com.example.newsapploginpage.model

data class News(
    val title: String,
    val content: String,
    val type: String,
    val imageUri: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)