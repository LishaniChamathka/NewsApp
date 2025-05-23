package com.example.newsapploginpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapploginpage.adapters.ViewPagerAdapter
import com.example.newsapploginpage.screens.FirstScreen
import com.example.newsapploginpage.screens.SecondScreen
import com.example.newsapploginpage.screens.ThirdScreen
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator


class OnboardingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
//            requireActivity().supportFragmentManager,
//            lifecycle
            childFragmentManager, // ✅ Use childFragmentManager to avoid conflicts
            viewLifecycleOwner.lifecycle
        )

        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)

        viewPager.adapter = adapter
        val indicator = view.findViewById<DotsIndicator>(R.id.dots_indicator)

        indicator.attachTo(viewPager)

        return view
    }


}