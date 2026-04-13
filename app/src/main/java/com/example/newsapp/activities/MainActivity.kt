package com.example.newsapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowInsets()
        setupCategoryClicks()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
    }

    private fun setupCategoryClicks() {
        binding.cardGeneral.setOnClickListener { openCategory("general") }
        binding.cardSports.setOnClickListener { openCategory("sports") }
        binding.cardEconomy.setOnClickListener { openCategory("economy") }
        binding.cardEntertainment.setOnClickListener { openCategory("entertainment") }
        binding.cardHealth.setOnClickListener { openCategory("health") }
        binding.cardTechnology.setOnClickListener { openCategory("technology") }
        binding.cardScience.setOnClickListener { openCategory("science") }
    }

    private fun openCategory(category: String) {
        val intent = Intent(this, NewsListActivity::class.java).apply {
            putExtra(NewsListActivity.EXTRA_CATEGORY, category)
        }
        startActivity(intent)
    }
}