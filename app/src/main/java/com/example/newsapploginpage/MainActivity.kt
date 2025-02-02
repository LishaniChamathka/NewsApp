package com.example.newsapploginpage

import android.os.Bundle
import android.text.TextUtils.replace
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

class MainActivity : AppCompatActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        loadFragment(OnboardingFragment())
//
//        // Load the onboarding fragment
////        if (savedInstanceState == null) {
////            supportFragmentManager.beginTransaction()
////                .replace(R.id.fragmentContainer, OnboardingFragment())
////                .commit()
////        }
//
//
//        // Load OnboardingFragment inside the container
//        private fun loadFragment(fragment: Fragment) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, fragment)
//                .commit()
//        }



        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // Load OnboardingFragment
//            loadFragment(OnboardingFragment())


            if (savedInstanceState == null) {
                supportFragmentManager.commit {
                    replace(R.id.fragmentContainer, OnboardingFragment())
                }
            }
        }

//        private fun loadFragment(fragment: Fragment) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, fragment)
//                .commit()
//        }




}