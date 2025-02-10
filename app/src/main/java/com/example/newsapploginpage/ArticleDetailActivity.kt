package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ArticleDetailActivity : BaseActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_article_detail_layout)

        val articleTitle = intent.getStringExtra("article_title") ?: "No Title"
        val articleDescription = intent.getStringExtra("article_description") ?: "No Description"
        val articleImage = intent.getStringExtra("article_image") // Could be a URL or resource ID

        val titleTextView: TextView = findViewById(R.id.articleTitle)
        val descTextView: TextView = findViewById(R.id.articleContent)
        val imageView: ImageView = findViewById(R.id.articleImage)

        val backToAdminHome: ImageView = findViewById(R.id.admin_back_btn)
        backToAdminHome.setOnClickListener {
            val intent = Intent(this,AdminHomePage::class.java)
            startActivity(intent)
        }

        val detailAcceptButton: Button = findViewById(R.id.acceptButton)
        val detailRejectButton: Button = findViewById(R.id.rejectButton)

        titleTextView.text = articleTitle
        descTextView.text = articleDescription

        // Load image based on URL or resource ID
        if (!articleImage.isNullOrEmpty()) {
            if (articleImage.startsWith("http")) {
                Glide.with(this)
                    .load(articleImage)
                    .placeholder(R.drawable.sport) // Placeholder while loading
                    .error(R.drawable.error_image) // Show on error
                    .into(imageView)
            } else {
                val imageResId = articleImage.toIntOrNull()
                imageView.setImageResource(imageResId ?: R.drawable.sport)
            }
        } else {
            imageView.setImageResource(R.drawable.sport)
        }

        detailAcceptButton.setOnClickListener {
            showAcceptPopup()
        }

        detailRejectButton.setOnClickListener {
            showDeletePopup()
        }
    }

    private fun showAcceptPopup() {
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

    private fun showDeletePopup() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_reject, null)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)

        val okButton = view.findViewById<Button>(R.id.reject_ok_button)
        val cancelText = view.findViewById<TextView>(R.id.reject_cancel_text)

        okButton.setOnClickListener {
            dialog.dismiss()
            showRejectSuccessDialog()
            // Implement rejection logic (e.g., send deletion request to the server)
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
}
