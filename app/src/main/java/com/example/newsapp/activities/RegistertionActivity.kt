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

class RegistertionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registertion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailET: EditText = findViewById(R.id.email_et)
        val passET: EditText = findViewById(R.id.pass_et)
        val confPassET: EditText = findViewById(R.id.conf_pass_et)
        val btn: Button = findViewById(R.id.sign_up_btn)
        val accountTV: TextView = findViewById(R.id.have_account_tv)
        val progress: ProgressBar = findViewById(R.id.progress)

        accountTV.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btn.setOnClickListener {
            val email = emailET.text.toString()
            val pass = passET.text.toString()
            val confPassword = confPassET.text.toString()

            if(email.isBlank() || pass.isBlank() || confPassword.isBlank())
                Toast.makeText(this, "Missing field/s", Toast.LENGTH_SHORT).show()
            else if(pass.length < 6)
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            else if(pass != confPassword)
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            else
                progress.isVisible = true
        }
    }
}