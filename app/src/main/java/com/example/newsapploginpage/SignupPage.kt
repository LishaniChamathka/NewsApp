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
import com.google.firebase.firestore.FirebaseFirestore

class SignupPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var reenterPasswordEditText: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        firstNameEditText = findViewById(R.id.firstName)
        lastNameEditText = findViewById(R.id.lastName)
        reenterPasswordEditText = findViewById(R.id.reenter_password)
        val signupButton = findViewById<Button>(R.id.signupButton)



        signupButton.setOnClickListener {
            if (validateInfo(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    firstNameEditText.text.toString(),
                    lastNameEditText.text.toString(),
                    reenterPasswordEditText.text.toString()
                )
            ) {
                createAccount( emailEditText.text.toString(), passwordEditText.text.toString())
            }
        }
    }

    private fun validateInfo(email: String, password: String, firstName: String, lastName: String, reenterPassword: String): Boolean {
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || reenterPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != reenterPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid
                userId?.let {
                    saveUserToFirestore(it, email, firstNameEditText.text.toString(), lastNameEditText.text.toString())
                }
                Log.d("TAG", "createUserWithEmail:success")
                Toast.makeText(baseContext, "Account created successfully.", Toast.LENGTH_SHORT).show()
                val prefs = getSharedPreferences("NewsAppPrefs", MODE_PRIVATE)
                prefs.edit().putBoolean("isLoggedIn", true).apply()
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
                finish()

            } else {
                Log.w("TAG", "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserToFirestore(userId: String, email: String, firstName: String, lastName: String) {
        val user = hashMapOf(
            "email" to email,
            "firstName" to firstName,
            "lastName" to lastName
        )

        db.collection("users").document(userId)
            .set(user)
            .addOnSuccessListener {
                Log.d("TAG", "DocumentSnapshot added with ID: $userId")
                Toast.makeText(baseContext, "Account created successfully.", Toast.LENGTH_SHORT).show()
                UserSession.setUserData(userId, firstName, lastName, email)
                finish()
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
                Toast.makeText(baseContext, "Error creating account.", Toast.LENGTH_SHORT).show()
            }
    }


}
