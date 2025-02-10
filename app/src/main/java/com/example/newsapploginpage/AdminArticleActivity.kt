package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapploginpage.adapters.AdminFragmentAdapter
import com.google.android.material.tabs.TabLayout

class AdminArticleActivity : AppCompatActivity(){
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: AdminFragmentAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_article)

        val backArticalButton: ImageView = findViewById(R.id.admin_artical_back_btn)
        backArticalButton.setOnClickListener {
            val intent = Intent(this,AdminHomePage::class.java)
            startActivity(intent)
        }

        tabLayout = findViewById(R.id.tabLayoutAdmin)
        viewPager2 = findViewById(R.id.viewPager2Admin)


        tabLayout.addTab(tabLayout.newTab().setText("Reject"))
        tabLayout.addTab(tabLayout.newTab().setText("Accept"))

        adapter = AdminFragmentAdapter(supportFragmentManager, lifecycle)
        viewPager2.adapter = adapter


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }
}