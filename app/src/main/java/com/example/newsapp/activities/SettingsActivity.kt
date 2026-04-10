package com.example.newsapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivitySettingsBinding
import com.example.newsapp.util.AppSettings

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedCountry = AppSettings.getSelectedCountry(this)
        binding.countryGroup.check(
            when (selectedCountry) {
                "gb" -> R.id.country_gb
                "eg" -> R.id.country_eg
                else -> R.id.country_us
            }
        )

        binding.saveButton.setOnClickListener {
            val country = when (binding.countryGroup.checkedRadioButtonId) {
                R.id.country_gb -> "gb"
                R.id.country_eg -> "eg"
                else -> "us"
            }
            AppSettings.saveSelectedCountry(this, country)
            finish()
        }
    }
}



