package com.example.newsapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.newsapp.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        val emailET: EditText = findViewById(R.id.email_et)
        val passET: EditText = findViewById(R.id.pass_et)
        val btn: Button = findViewById(R.id.login_btn)
        val accountTV: TextView = findViewById(R.id.no_account_tv)
        val progress: ProgressBar = findViewById(R.id.progress)
        val forgotPassTV: TextView = findViewById(R.id.forgot_pass_tv)

        accountTV.setOnClickListener {
            startActivity(Intent(this, RegistertionActivity::class.java))
            finish()
        }

        btn.setOnClickListener {
            val email = emailET.text.toString()
            val pass = passET.text.toString()

            if(email.isBlank() || pass.isBlank())
                Toast.makeText(this, "Missing field", Toast.LENGTH_SHORT).show()
            else
                progress.isVisible = true
        }
        forgotPassTV.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}