package com.example.newsapploginpage

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        val prefs: SharedPreferences = getSharedPreferences("NewsAppPrefs", MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            val intent = Intent(this, LoginPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
