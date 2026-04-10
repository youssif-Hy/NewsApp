package com.example.newsapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.R
import com.example.newsapp.databinding.ArticleListItemBinding
import com.example.newsapp.shared.Article

class NewsAdapter(
    private val a: Activity,
    private val articles: ArrayList<Article>,
    private val isGuest: Boolean
) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(val binding: ArticleListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val b = ArticleListItemBinding. inflate(LayoutInflater. from(parent.context), parent, false)
        return NewsViewHolder(b)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        val article = articles[position]
        val title = article.title ?: a.getString(R.string.no_title)
        holder.binding.articleText.text = title

        Glide
            .with(holder.binding.articleImage)
            .load(article.urlToImage)
            .placeholder(R.drawable.broken_image)
            .error(R.drawable.broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.articleImage)

        val url = article.url
        holder.binding.articleContainer.setOnClickListener {
            if (url.isNullOrBlank()) {
                Toast.makeText(a, a.getString(R.string.invalid_article_url), Toast.LENGTH_SHORT).show()
            } else {
                val i = Intent(Intent.ACTION_VIEW, url.toUri())
                a.startActivity(i)
            }
        }
        holder.binding. shareFab.setOnClickListener {
            if (url.isNullOrBlank()) {
                Toast.makeText(a, a.getString(R.string.invalid_article_url), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            ShareCompat
                .IntentBuilder(a)
                .setType("text/plain")
                .setChooserTitle("Share article with:")
                .setText(url)
                .startChooser()
        }

        holder.binding.favoriteButton.alpha = if (isGuest) 0.5f else 1f
        holder.binding.favoriteButton.setOnClickListener {
            if (isGuest) {
                Toast.makeText(a, a.getString(R.string.login_required_favorite), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun getItemCount() = articles.size

}

