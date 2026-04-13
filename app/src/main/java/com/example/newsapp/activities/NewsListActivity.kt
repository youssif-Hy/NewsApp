package com.example.newsapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.newsapp.BuildConfig
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.ActivityNewsListBinding
import com.example.newsapp.shared.Article
import com.example.newsapp.shared.News
import com.example.newsapp.shared.NewsApiService
import com.example.newsapp.util.AppSettings
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsListBinding
    private val isGuest = true // this will be removed later

    private enum class UiState {
        LOADING,
        CONTENT,
        EMPTY,
        ERROR
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val category = intent.getStringExtra(EXTRA_CATEGORY) ?: "general"
        binding.topAppBar.title = category.replaceFirstChar { it.uppercase() }

        binding.topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.favorite -> {
                    Toast.makeText(this, getString(R.string.login_required_favorite), Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                R.id.more -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.swipeRefresh.setOnRefreshListener {
            loadNews(category)
        }

        loadNews(category)
    }

    override fun onResume() {
        super.onResume()
        val category = intent.getStringExtra(EXTRA_CATEGORY) ?: "general"
        loadNews(category)
    }

    private fun loadNews(category: String) {
        val country = AppSettings.getSelectedCountry(this)
        renderState(UiState.LOADING)

        val apiKey = BuildConfig.NEWS_API_KEY
        if (apiKey.isBlank()) {
            renderState(UiState.ERROR, getString(R.string.missing_api_key))
            return
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsApiService = retrofit.create(NewsApiService::class.java)
        newsApiService.getTopHeadlines(
            country = country,
            category = category,
            pageSize = 30,
            apiKey = apiKey
        ).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (!response.isSuccessful) {
                    val apiMessage = parseApiMessage(response)
                    renderState(UiState.ERROR, apiMessage)
                    return
                }

                val articles = response.body()?.articles
                    .orEmpty()
                    .filterNot { it.title == "[Removed]" }
                    .filter { !it.url.isNullOrBlank() }
                    .toCollection(ArrayList())

                if (articles.isEmpty()) {
                    val emptyMessage = getString(R.string.no_headlines_for_filters, category, country)
                    renderState(UiState.EMPTY, emptyMessage)
                } else {
                    showNews(articles)
                    renderState(UiState.CONTENT)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("trace", "Error: ${t.message}")
                renderState(UiState.ERROR, getString(R.string.fetch_failed))
            }
        })
    }

    private fun renderState(state: UiState, message: String? = null) {
        binding.progress.isVisible = state == UiState.LOADING
        binding.newsList.isVisible = state == UiState.CONTENT
        binding.statusText.isVisible = state == UiState.EMPTY || state == UiState.ERROR
        binding.statusText.text = message ?: ""
        binding.swipeRefresh.isRefreshing = false
    }

    private fun parseApiMessage(response: Response<News>): String {
        val fallback = getString(R.string.fetch_failed)
        val body = response.errorBody()?.string() ?: return fallback
        return try {
            JSONObject(body).optString("message").ifBlank { fallback }
        } catch (_: Exception) {
            fallback
        }
    }

    private fun showNews(articles: ArrayList<Article>) {
        val adapter = NewsAdapter(this, articles, isGuest)
        binding.newsList.adapter = adapter
    }

    companion object {
        const val EXTRA_CATEGORY = "extra_category"
    }
}



