package com.example.newsapploginpage.screens

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


class FirstScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first_screen, container, false)

        val next = view.findViewById<TextView>(R.id.btnNext1)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        next.setOnClickListener {
            viewPager?.currentItem = 1
        }

        val skipTextView: TextView = view.findViewById(R.id.skipText1)
        skipTextView.setOnClickListener {
            val intent = Intent(requireContext(), LoginPage::class.java)
            startActivity(intent)
            requireActivity().finish() // ✅ Prevents fragment transaction issues
        }

        return view
    }


}