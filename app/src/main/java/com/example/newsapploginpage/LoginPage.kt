package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.example.newsapploginpage.UserSession


class LoginPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var db: FirebaseFirestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_login_page)

        emailEditText = findViewById(R.id.login_email)
        passwordEditText = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.loginButton)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val signupButton: Button = findViewById(R.id.btnSignup)
        signupButton.setOnClickListener {
            val intent = Intent(this, SignupPage::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginAccount(email, password)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
        }
    }

    private fun loginAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid
                if (userId != null) {
                    db.collection("users").document(userId).get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                val firstName = document.getString("firstName") ?: "N/A"
                                val lastName = document.getString("lastName") ?: "N/A"
                                val userEmail = document.getString("email") ?: "N/A"
                                val role = document.getString("role") ?: "N/A"
                                val prefs = getSharedPreferences("NewsAppPrefs", MODE_PRIVATE)
                                prefs.edit().putBoolean("isLoggedIn", true).apply()
                                UserSession.setUserData(userId, firstName, lastName, userEmail)
                                if (role == "admin") {
                                    val intent = Intent(this, AdminHomePage::class.java)
                                    startActivity(intent)
                                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else {
                                val intent = Intent(this, Homepage::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            } else {
                                Log.e("LoginActivity", "User document does not exist")
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e("LoginActivity", "Error fetching user data", e)
                        }
                }
            } else {
                Log.e("LoginActivity", "Login Failed", task.exception)
            }
        }
    }

}

