package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapploginpage.model.AdminArticles
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore


class AdminHomePage : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var articleList: MutableList<AdminArticles>
    private lateinit var adapter: AdminArticlesAdapter
    private lateinit var db: FirebaseFirestore

    private lateinit var bottomNavigationView: BottomNavigationView

    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_page)

        recyclerView = findViewById(R.id.adminArticlesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        articleList = mutableListOf()
        adapter = AdminArticlesAdapter(articleList)

        recyclerView.adapter = adapter
        db = FirebaseFirestore.getInstance()


        //navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        // Handle menu item selection
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

        fetchPendingArticles()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_menubar, menu)
        return true
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



    // fetch pending articles from Firestore
    private fun fetchPendingArticles() {
        db.collection("articles")
            .whereEqualTo("status", "pending")
            .get()
            .addOnSuccessListener { documents ->
                articleList.clear()
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
                        adminContent = shortContent,
                        fullContent = fullContent,
                        adminImageUrl = document.getString("imageUrl")!!
                    )
                    Log.d("AdminHomePage", "Article: $article")
                    setImageResource(article.adminImageUrl)
                    articleList.add(article)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error fetching articles: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setImageResource(userImageUrl: String) {

    }


    // Custom Adapter for RecyclerView
    inner class AdminArticlesAdapter(private val articles: List<AdminArticles>) : RecyclerView.Adapter<AdminArticlesAdapter.ArticleViewHolder>() {

        inner class ArticleViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.adminArticleTitle)
            val descriptionTextView: TextView = itemView.findViewById(R.id.adminArticleDescription)
            val articleImageView: ImageView = itemView.findViewById(R.id.adminArticleImage)
            val acceptButton: Button = itemView.findViewById(R.id.adminApproveButton)
            val rejectButton: Button = itemView.findViewById(R.id.adminRejectButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
            val view = LayoutInflater.from(this@AdminHomePage).inflate(R.layout.activity_admin_article_item, parent, false)
            return ArticleViewHolder(view)
        }

        override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
            val article = articles[position]
            holder.titleTextView.text = article.adminTitle
            holder.descriptionTextView.text = article.adminContent

            Glide.with(holder.itemView.context)
                .load(article.adminImageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.articleImageView)

            holder.itemView.setOnClickListener {
                openArticleDetail(article)
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

    }

    // Reusable dialog function to reduce redundancy
    @SuppressLint("InflateParams")
    private fun showAcceptPopup(layout: AdminArticles, onDismiss: () -> Unit = {}) {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_accept, null)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)

        val okButton = view.findViewById<Button>(R.id.admin_ok_button)
        val cancelText = view.findViewById<TextView>(R.id.admin_cancel_text)

        okButton.setOnClickListener {
            db.collection("articles")
                .document(layout.adminId)
                .update("status", "approved")
                .addOnSuccessListener {
                    Toast.makeText(this, "Article approved successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    fetchPendingArticles() // Refresh the list of pending articles
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error approving article: ${e.message}", Toast.LENGTH_SHORT).show()
                }
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
    private fun showDeletePopup(layout: AdminArticles, onDismiss: () -> Unit = {}) {
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
    private fun openArticleDetail(article: AdminArticles) {
        val intent = Intent(this, ArticleDetailActivity::class.java).apply {
            putExtra("title", article.adminTitle)
            putExtra("description", article.fullContent)
            putExtra("image", article.adminImageUrl)
            putExtra("timeAgo", article.adminTime)
            putExtra("author", article.adminSource)
        }
        startActivity(intent)
    }


    // Custom Adapter for RecyclerView
//    inne r class AdminArticlesAdapter(private val articles: List<
//
//                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
//            val view = LayoutInflater.from(this@AdminHomePage).inflate(R.layout.activity_admin_article_item, parent, false)
//            returnArticleViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
//            val article = articles[position]
//            holder.titleTextView.text = article.title
//            holder.descriptionTextView.text = article.description
//            Glide.with(this@AdminHomePage)
//                .load(article.imageUrl)
//                .placeholder(R.drawable.sport) // Placeholder image
//                .into(holder.articleImageView)
//
//            holder.itemView.setOnClickListener {
//                val intent = Intent(this@AdminHomePage, ArticleDetailActivity::class.java).apply {
//                    putExtra("article_title", article.title)
//                    putExtra("article_description", article.description)
//                    putExtra("article_image", article.imageUrl)
//                }
//                startActivity(intent)
//            }
//
//            holder.acceptButton.setOnClickListener {
//                showAcceptPopup(article)
//            }
//
//            holder.rejectButton.setOnClickListener {
//                showDeletePopup(article)
//            }
//        }
//
//        override fun getItemCount(): Int = articles.size
//
//        // ViewHolder for RecyclerView item
//        inner class ArticleViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
//            val titleTextView: TextView = itemView.findViewById(R.id.adminArticleTitle)
//            val descriptionTextView: TextView = itemView.findViewById(R.id.adminArticleDescription)
//            val articleImageView: ImageView = itemView.findViewById(R.id.adminArticleImage)
//            val acceptButton: Button = itemView.findViewById(R.id.adminApproveButton)
//            val rejectButton: Button = itemView.findViewById(R.id.adminRejectButton)
//        }
//    }
//
//    // Reusable dialog function to reduce redundancy
//    @SuppressLint("InflateParams")
//    private fun showAcceptPopup(layout: Article, onDismiss: () -> Unit = {}) {
//        val dialog = Dialog(this)
//        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_accept, null)
//
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(view)
//
//        val okButton = view.findViewById<Button>(R.id.admin_ok_button)
//        val cancelText = view.findViewById<TextView>(R.id.admin_cancel_text)
//
//        okButton.setOnClickListener {
//            dialog.dismiss()
//            showAcceptSuccessDialog()
//        }
//
//        cancelText.setOnClickListener {
//            dialog.dismiss()
//        }
//
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.window?.setLayout(
//            (resources.displayMetrics.widthPixels * 0.85).toInt(),
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )
//
//        dialog.show()
//    }
//
//    @SuppressLint("MissingInflatedId", "InflateParams")
//    private fun showDeletePopup(layout: Article, onDismiss: () -> Unit = {}) {
//        val dialog = Dialog(this)
//        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_reject, null)
//
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(view)
//
//        val okRejectButton = view.findViewById<Button>(R.id.reject_ok_button)
//        val cancelRejectText = view.findViewById<TextView>(R.id.reject_cancel_text)
//
//        okRejectButton.setOnClickListener {
//            dialog.dismiss()
//            showRejectSuccessDialog()
//        }
//
//        cancelRejectText.setOnClickListener {
//            dialog.dismiss()
//        }
//
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.window?.setLayout(
//            (resources.displayMetrics.widthPixels * 0.85).toInt(),
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )
//
//        dialog.show()
//    }
//
//    @SuppressLint("InflateParams")
//    private fun showAcceptSuccessDialog() {
//        val dialog = Dialog(this)
//        val view = LayoutInflater.from(this).inflate(R.layout.dialog_accept, null)
//
//        dialog.setContentView(view)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.window?.setLayout(
//            (resources.displayMetrics.widthPixels * 0.85).toInt(),
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )
//
//        val viewAcceptArticleButton = view.findViewById<Button>(R.id.viewAdminAcceptArticleButton)
//        val backToHomeAdminButton = view.findViewById<Button>(R.id.backToHomeButtonAdmin)
//
//        viewAcceptArticleButton.setOnClickListener {
//            dialog.dismiss()
//            startActivity(Intent(this, AdminArticleActivity::class.java))
//        }
//
//        backToHomeAdminButton.setOnClickListener {
//            dialog.dismiss()
//            startActivity(Intent(this, AdminHomePage::class.java))
//        }
//
//        dialog.show()
//    }
//
//    @SuppressLint("MissingInflatedId", "InflateParams")
//    private fun showRejectSuccessDialog() {
//        val dialog = Dialog(this)
//        val view = LayoutInflater.from(this).inflate(R.layout.dialog_reject, null)
//
//        dialog.setContentView(view)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.window?.setLayout(
//            (resources.displayMetrics.widthPixels * 0.85).toInt(),
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )
//
//        val viewRejectArticleButton = view.findViewById<Button>(R.id.viewAdminRejectArticleButton)
//        val backToHomeRejectButton = view.findViewById<Button>(R.id.backToHomeButtonAdmin)
//
//        viewRejectArticleButton.setOnClickListener {
//            dialog.dismiss()
//            startActivity(Intent(this, AdminArticleActivity::class.java))
//        }
//
//        backToHomeRejectButton.setOnClickListener {
//            dialog.dismiss()
//            startActivity(Intent(this, AdminHomePage::class.java))
//        }
//
//        dialog.show()
//    }

    // Fetch pending articles from Firestore

//    // Open article detail when a specific article is clicked
//    private fun openArticleDetail(article: Article) {
//        val intent = Intent(this, ArticleDetailActivity::class.java).apply {
//            putExtra("title", article.title)
//            putExtra("description", article.description)
//            putExtra("image", article.imageUrl)
//        }
//        startActivity(intent)
//    }
}