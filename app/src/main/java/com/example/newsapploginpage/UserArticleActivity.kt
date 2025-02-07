package com.example.newsapploginpage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapploginpage.adapters.ViewPagerAdapter
import com.example.tabnavigation.PublishActivity
import com.example.tabnavigation.SubmitActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_article)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        // Add tabs to the TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Publish"))
        tabLayout.addTab(tabLayout.newTab().setText("Submit"))

        // Set initial activity (e.g., PublishActivity)
        navigateToActivity(PublishActivity::class.java)

        // Handle tab selection
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> navigateToActivity(PublishActivity::class.java)
                    1 -> navigateToActivity(SubmitActivity::class.java)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}