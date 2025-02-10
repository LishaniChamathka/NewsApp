package com.example.newsapploginpage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils.replace
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.newsapploginpage.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

//                supportFragmentManager.commit {
//                    replace(R.id.fragmentContainer, OnboardingFragment())
//                }
//            }

//            Handler(Looper.getMainLooper()).post {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainer, OnboardingFragment())
//                    .commit()
//            }

//            supportFragmentManager.executePendingTransactions()
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, OnboardingFragment())
//                .commit()

//            if (savedInstanceState == null) {

            Log.d("FragmentTransaction", "Before committing fragment")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, OnboardingFragment())
                .commit()
            Log.d("FragmentTransaction", "After committing fragment")

        }
}