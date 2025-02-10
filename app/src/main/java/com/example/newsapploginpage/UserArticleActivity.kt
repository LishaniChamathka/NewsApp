package com.example.newsapploginpage


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapploginpage.adapters.FragmentPageAdapter
import com.google.android.material.tabs.TabLayout

class UserArticleActivity : BaseActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: FragmentPageAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_article)

        val backArtical: ImageView = findViewById(R.id.artical_back_btn)
        backArtical.setOnClickListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }

        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager2)


        tabLayout.addTab(tabLayout.newTab().setText("Publish"))
        tabLayout.addTab(tabLayout.newTab().setText("Submit"))

        adapter = FragmentPageAdapter(supportFragmentManager, lifecycle)
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