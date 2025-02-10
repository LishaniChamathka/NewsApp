package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//firebase signout
import com.google.firebase.auth.FirebaseAuth
import com.example.newsapploginpage.UserSession
import com.google.api.ResourceDescriptor.History

class ProfileActivity : BaseActivity() {
    private lateinit var logoutView: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var name: TextView
    private lateinit var history: TextView



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_profile)

        logoutView = findViewById(R.id.logout)
        name = findViewById(R.id.profile_name)
        history = findViewById(R.id.history)

        val fullname = UserSession.firstName + " " + UserSession.lastName
        name.text = fullname

        findViewById<TextView>(R.id.addNews).setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }

        history.setOnClickListener {
            val intent = Intent(this, UserArticleActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_profile_back).setOnClickListener {
            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
        }

        logoutView.setOnClickListener {
            auth.signOut()
            val prefs = getSharedPreferences("NewsAppPrefs", MODE_PRIVATE)
            prefs.edit().putBoolean("isLogged", false).apply()
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }
    }
}