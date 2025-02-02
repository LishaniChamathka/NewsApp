package com.example.newsapploginpage.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapploginpage.LoginPage
import com.example.newsapploginpage.R

class FourthScreen : Fragment() {


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fourth_screen, container, false)

        val next = view.findViewById<TextView>(R.id.btnNext4)

        next.setOnClickListener {
            val intent = Intent(activity, LoginPage::class.java)
            startActivity(intent)
        }


        return view
    }

}