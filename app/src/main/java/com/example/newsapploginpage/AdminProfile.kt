package com.example.newsapploginpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
class AdminProfile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_profile_activity)

        auth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.logout).setOnClickListener {
            auth.signOut()
            val prefs = getSharedPreferences("NewsAppPrefs", MODE_PRIVATE)
            prefs.edit().putBoolean("isLogged", false).apply()
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.btn_admin_profile_back).setOnClickListener {
            val intent = Intent(this, AdminHomePage::class.java)
            startActivity(intent)
        }


    }
}