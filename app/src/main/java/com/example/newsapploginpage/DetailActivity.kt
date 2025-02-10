package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : BaseActivity() {
    private fun showBottomSheetMenu() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)

        bottomSheetView.findViewById<LinearLayout>(R.id.ll_translate).setOnClickListener {
            // Translate action
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<LinearLayout>(R.id.ll_share).setOnClickListener {
            // Share action
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<LinearLayout>(R.id.ll_save).setOnClickListener {
            // Save action
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<LinearLayout>(R.id.ll_voice_read).setOnClickListener {
            // Voice read action
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<LinearLayout>(R.id.ll_similar_news).setOnClickListener {
            // Similar news action
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<LinearLayout>(R.id.ll_report).setOnClickListener {
            // Report action
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        findViewById<View>(R.id.detail_menu_btn).setOnClickListener {
            showBottomSheetMenu()
        }

        val title = intent.getStringExtra("news_title")
        val category = intent.getStringExtra("news_category")
        val imageRes = intent.getIntExtra("news_image", 0)
        val detail = intent.getStringExtra("news_in_detail")

        val imgNews: ImageView = findViewById(R.id.imgNewsDetail)
        val tvTitle: TextView = findViewById(R.id.tvNewsTitleDetail)
        val tvCategory: TextView = findViewById(R.id.tvNewsCategoryDetail)
        val newsDetail: TextView = findViewById(R.id.tvNewsInDetail)
        val newsImage = intent.getIntExtra("news_image", -1)  // Default to -1 if not found


        tvTitle.text = title
        tvCategory.text = category
        imgNews.setImageResource(imageRes)
        newsDetail.text = detail

        val back: ImageView = findViewById(R.id.detail_back_btn)
        back.setOnClickListener {
            val intent = Intent(this,Homepage::class.java)
            startActivity(intent)
        }

    }
}
