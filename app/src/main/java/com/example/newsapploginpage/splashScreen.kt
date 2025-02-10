package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "NewsAppPrefs"
        private const val KEY_FIRST_RUN = "isFirstRun"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean(KEY_FIRST_RUN, true)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = if (isFirstRun) {

                prefs.edit().putBoolean(KEY_FIRST_RUN, false).apply()
                Intent(this, LoginPage::class.java)
            } else {

                    Intent(this, LoginPage::class.java)

            }

            startActivity(intent)
            finish()
        }, 2000) // 2-second delay
    }
}
