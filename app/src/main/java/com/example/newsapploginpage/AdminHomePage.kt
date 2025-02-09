package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapploginpage.adapters.ArticleAdapter
import com.example.newsapploginpage.model.Article

class AdminHomePage : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_page)

        recyclerView = findViewById(R.id.adminArticlesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val articles = listOf(
            Article("T20 world up win goes to the Indian cricket...", "Lorem ipsum dolor sit amet", R.drawable.sport.toString()),
            Article("Another Article Title", "Another article description", R.drawable.sport.toString()),
            Article("Third Article Title", "More details about this", R.drawable.sport.toString()),
            Article("T20 world up win goes to the Indian cricket...", "Lorem ipsum dolor sit amet", R.drawable.sport.toString()),
            Article("Another Article Title", "Another article description", R.drawable.sport.toString()),
            Article("Third Article Title", "More details about this", R.drawable.sport.toString()),
            Article("T20 world up win goes to the Indian cricket...", "Lorem ipsum dolor sit amet", R.drawable.sport.toString()),
            Article("Another Article Title", "Another article description", R.drawable.sport.toString()),
            Article("Third Article Title", "More details about this", R.drawable.sport.toString())
        )

        articleAdapter = ArticleAdapter(this, articles, { article ->
            showAcceptPopup(article)
        }, { article ->
            openArticleDetail(article)
        })

        recyclerView.adapter = articleAdapter
    }

    private fun showAcceptPopup(article: Article) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Accept Article")
        builder.setMessage("Do you want to accept '${article.title}'?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun openArticleDetail(article: Article) {
        val intent = Intent(this, ArticleDetailActivity::class.java).apply {
            putExtra("title", article.title)
            putExtra("description", article.description)
            putExtra("image", article.imageUrl)
        }
        startActivity(intent)
    }
}
