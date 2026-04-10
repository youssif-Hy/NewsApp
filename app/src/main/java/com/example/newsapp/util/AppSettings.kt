package com.example.newsapp.util

import android.content.Context
import androidx.core.content.edit

object AppSettings {
    private const val PREFS_NAME = "news_settings"
    private const val KEY_COUNTRY = "country"

    val supportedCountries = listOf("us", "gb", "eg")

    fun getSelectedCountry(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val saved = prefs.getString(KEY_COUNTRY, supportedCountries.first())
        return if (saved in supportedCountries) saved!! else supportedCountries.first()
    }

    fun saveSelectedCountry(context: Context, country: String) {
        if (country !in supportedCountries) return
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { putString(KEY_COUNTRY, country) }
    }
}


