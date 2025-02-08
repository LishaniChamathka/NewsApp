package com.example.newsapploginpage

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapploginpage.adapters.BreakingNewsAdapter
import com.example.newsapploginpage.adapters.TodayNewsAdapter
import com.example.newsapploginpage.databinding.ActivityHomepageBinding
import com.example.newsapploginpage.databinding.ActivityMainBinding
import com.example.newsapploginpage.model.NewsItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class Homepage : AppCompatActivity() {
    private lateinit var binding: ActivityHomepageBinding

    private lateinit var breakingNewsAdapter: BreakingNewsAdapter
    private lateinit var todayNewsAdapter: TodayNewsAdapter
    private lateinit var breakingNewsList: List<NewsItem>
    private lateinit var todayNewsList: List<NewsItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        binding.bottomNavigationView.selectedItemId = R.id.nav_home

//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)
        searchView.clearFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterNews(newText)
                return true
            }
        })

        // Sample data
        breakingNewsList = listOf(
            NewsItem(
                "Olympic women's badminton championship went to India",
                "Sports",
                R.drawable.breaking1,
                "Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget nisi urna sed. At in feugiat tristique bibendum sapien. Velit at pellentesque id a ac. Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget nisi urna sed. At in feugiat tristique bibendum sapien.\n" +
                        "\n" +
                        " Velit at pellentesque id a ac. Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget nisi urna sed. At in feugiat tristique bibendum sapien. ",
                "Ada Derana",
                "2 Hours ago"
            ),
            NewsItem(
                "Sri Lanka faces major floods",
                "Weather",
                R.drawable.breaking2,
                "Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget nisi urna sed. At in feugiat tristique bibendum sapien. Velit at pellentesque id a ac. Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget nisi urna sed. At in feugiat tristique bibendum sapien.\n" +
                        "\n" +
                        " Velit at pellentesque id a ac. Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget nisi urna sed. At in feugiat tristique bibendum sapien.Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget ",
                "Ada Derana",
                "2 Hours ago"
            )
        )

        todayNewsList = listOf(
            NewsItem(
                "Grade 5 scholarship exam results released",
                "Educational",
                R.drawable.today1,
                "Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget nisi urna sed. At in feugiat tristique bibendum sapien. Velit at pellentesque id a ac. Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget nisi urna sed. At in feugiat tristique bibendum sapien.\n" +
                        "\n" +
                        " Velit at pellentesque id a ac. Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget nisi urna sed. At in feugiat tristique bibendum sapien. ",
                "Ada Derana",
                "2 Hours ago"
            ),
            NewsItem(
                "Sri Lankan president departs for China visit",
                "Politics",
                R.drawable.today2,
                "Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget nisi urna sed. At in feugiat tristique bibendum sapien. Velit at pellentesque id a ac. Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget nisi urna sed. At in feugiat tristique bibendum sapien.\n" +
                        "\n" +
                        " Velit at pellentesque id a ac. Lorem ipsum dolor sit amet consectetur. Euismod non ut est fermentum fringilla ipsum risus nunc. Velit sed posuere tortor sagittis fringilla eget nisi urna sed. At in feugiat tristique bibendum sapien. ",
                "Ada Derana",
                "2 Hours ago"
            )
        )

        // Setting up Breaking News RecyclerView (Horizontal)
        val recyclerBreakingNews: RecyclerView = findViewById(R.id.recyclerBreakingNews)
        recyclerBreakingNews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        breakingNewsAdapter = BreakingNewsAdapter(breakingNewsList) { selectedNews ->
            openDetailPage(selectedNews)
        }
        recyclerBreakingNews.adapter = breakingNewsAdapter

        // Setting up Today News RecyclerView (Vertical)
        val recyclerTodayNews: RecyclerView = findViewById(R.id.recyclerTodayNews)
        recyclerTodayNews.layoutManager = LinearLayoutManager(this)
        todayNewsAdapter = TodayNewsAdapter(todayNewsList) { selectedNews ->
            openDetailPage(selectedNews)
        }
        recyclerTodayNews.adapter = todayNewsAdapter
    }

    private fun openDetailPage(news: NewsItem) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("news_title", news.title)
            putExtra("news_category", news.category)
            putExtra("news_image", news.imageRes)
            putExtra("news_in_detail", news.detail)
        }
        startActivity(intent)
    }

    private fun filterNews(query: String?) {
        val filteredBreakingNews = if (!query.isNullOrEmpty()) {
            breakingNewsList.filter { it.title.contains(query, ignoreCase = true) }
        } else {
            breakingNewsList
        }

        val filteredTodayNews = if (!query.isNullOrEmpty()) {
            todayNewsList.filter { it.title.contains(query, ignoreCase = true) }
        } else {
            todayNewsList
        }

        breakingNewsAdapter.updateList(filteredBreakingNews)
        todayNewsAdapter.updateList(filteredTodayNews)
    }

//    private fun setupBottomNavigation() {
//        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
////                R.id.nav_home -> {
////                    // Handle home navigation
////                    true
////                }
////                R.id.nav_explore -> {
////                    // Handle explore navigation
////                    true
////                }
////                R.id.nav_bookmarks -> {
////                    // Handle bookmarks navigation
////                    true
////                }
//                R.id.nav_profile -> {
//                    // Navigate to Profile Activity
//                    startActivity(Intent(this, ProfileActivity::class.java))
//                    true
//                }
//                else -> false
//            }
//        }
//    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId != binding.bottomNavigationView.selectedItemId) {
                when (menuItem.itemId) {
                    R.id.nav_home -> {
                        startActivity(Intent(this, Homepage::class.java))
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    R.id.nav_explore -> {
                       startActivity(Intent(this, ExploreActivity::class.java))
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    R.id.nav_bookmarks -> {
//                        startActivity(Intent(this, BookmarksActivity::class.java))
//                        overridePendingTransition(0, 0)
//                        finish()
                    }
                    R.id.nav_profile -> {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        overridePendingTransition(0, 0)
                        finish()
                    }
                }
            }
            true
        }
    }


}
