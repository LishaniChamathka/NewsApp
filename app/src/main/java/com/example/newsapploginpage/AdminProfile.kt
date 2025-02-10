package com.example.newsapploginpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AdminProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_profile_activity)

        findViewById<Button>(R.id.btn_admin_profile_back).setOnClickListener {
            val intent = Intent(this, AdminHomePage::class.java)
            startActivity(intent)
        }
    }
}