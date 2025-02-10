package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapploginpage.model.Article
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminHomePage : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var articleList: List<Article>

    private lateinit var bottomNavigationView: BottomNavigationView

    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_page)

        //navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bottomNavigationView = findViewById(R.id.adminBottomNav)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.adminHome -> {
                    // Stay in the same activity (AdminHomePage)
                    true
                }
                R.id.adminArticles -> {
                    startActivity(Intent(this, AdminArticleActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.adminProfile -> {
                    startActivity(Intent(this, AdminProfile::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }

        // Sample article data
        articleList = listOf(
            Article(
                "T20 World Cup Win",
                "Lorem ipsum dolor sit amet",
                R.drawable.sport.toString(),
                "Bloomberg",
                "android.resource://${this.packageName}/${R.drawable.today1}"
            ),
            Article(
                "Another Article",
                "Another description",
                R.drawable.sport.toString(),
                "Bloomberg",
                "android.resource://${this.packageName}/${R.drawable.today1}"
            ),
            Article(
                "Tech Innovations",
                "Future of AI in 2025",
                R.drawable.sport.toString(),
                "Bloomberg",
                "android.resource://${this.packageName}/${R.drawable.today1}"
            )
        )

        // RecyclerView setup
        recyclerView = findViewById(R.id.adminArticlesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdminArticlesAdapter(articleList)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_menubar, menu)
        return true
    }



    // Handle menu item selection







    // Custom Adapter for RecyclerView
    inner class AdminArticlesAdapter(private val articles: List<Article>) : RecyclerView.Adapter<AdminArticlesAdapter.ArticleViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
            val view = LayoutInflater.from(this@AdminHomePage).inflate(R.layout.activity_admin_article_item, parent, false)
            return ArticleViewHolder(view)
        }

        override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
            val article = articles[position]
            holder.titleTextView.text = article.title
            holder.descriptionTextView.text = article.description
            Glide.with(this@AdminHomePage)
                .load(article.imageUrl)
                .placeholder(R.drawable.sport) // Placeholder image
                .into(holder.articleImageView)

            holder.itemView.setOnClickListener {
                val intent = Intent(this@AdminHomePage, ArticleDetailActivity::class.java).apply {
                    putExtra("article_title", article.title)
                    putExtra("article_description", article.description)
                    putExtra("article_image", article.imageUrl)
                }
                startActivity(intent)
            }

            holder.acceptButton.setOnClickListener {
                showAcceptPopup(article)
            }

            holder.rejectButton.setOnClickListener {
                showDeletePopup(article)
            }
        }

        override fun getItemCount(): Int = articles.size

        // ViewHolder for RecyclerView item
        inner class ArticleViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.adminArticleTitle)
            val descriptionTextView: TextView = itemView.findViewById(R.id.adminArticleDescription)
            val articleImageView: ImageView = itemView.findViewById(R.id.adminArticleImage)
            val acceptButton: Button = itemView.findViewById(R.id.adminApproveButton)
            val rejectButton: Button = itemView.findViewById(R.id.adminRejectButton)
        }
    }

    // Reusable dialog function to reduce redundancy
    @SuppressLint("InflateParams")
    private fun showAcceptPopup(layout: Article, onDismiss: () -> Unit = {}) {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_accept, null)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)

        val okButton = view.findViewById<Button>(R.id.admin_ok_button)
        val cancelText = view.findViewById<TextView>(R.id.admin_cancel_text)

        okButton.setOnClickListener {
            dialog.dismiss()
            showAcceptSuccessDialog()
        }

        cancelText.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialog.show()
    }

    @SuppressLint("MissingInflatedId", "InflateParams")
    private fun showDeletePopup(layout: Article, onDismiss: () -> Unit = {}) {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_reject, null)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)

        val okRejectButton = view.findViewById<Button>(R.id.reject_ok_button)
        val cancelRejectText = view.findViewById<TextView>(R.id.reject_cancel_text)

        okRejectButton.setOnClickListener {
            dialog.dismiss()
            showRejectSuccessDialog()
        }

        cancelRejectText.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialog.show()
    }

    @SuppressLint("InflateParams")
    private fun showAcceptSuccessDialog() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_accept, null)

        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val viewAcceptArticleButton = view.findViewById<Button>(R.id.viewAdminAcceptArticleButton)
        val backToHomeAdminButton = view.findViewById<Button>(R.id.backToHomeButtonAdmin)

        viewAcceptArticleButton.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, AdminArticleActivity::class.java))
        }

        backToHomeAdminButton.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, AdminHomePage::class.java))
        }

        dialog.show()
    }

    @SuppressLint("MissingInflatedId", "InflateParams")
    private fun showRejectSuccessDialog() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_reject, null)

        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val viewRejectArticleButton = view.findViewById<Button>(R.id.viewAdminRejectArticleButton)
        val backToHomeRejectButton = view.findViewById<Button>(R.id.backToHomeButtonAdmin)

        viewRejectArticleButton.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, AdminArticleActivity::class.java))
        }

        backToHomeRejectButton.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, AdminHomePage::class.java))
        }

        dialog.show()
    }

    // Open article detail when a specific article is clicked
    private fun openArticleDetail(article: Article) {
        val intent = Intent(this, ArticleDetailActivity::class.java).apply {
            putExtra("title", article.title)
            putExtra("description", article.description)
            putExtra("image", article.imageUrl)
        }
        startActivity(intent)
    }
}
