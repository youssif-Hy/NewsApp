package com.example.newsapp.activities

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsapp.databinding.ActivityForgotPassBinding

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPassBinding
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.emailInput) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupToolbar()
        setupSendOtp()
        setupVerifyButton()
    }

    private fun setupToolbar() {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupSendOtp() {
        binding.tvSendOtp.setOnClickListener {
            if (isTimerRunning) return@setOnClickListener

            val email = binding.emailInput.text.toString().trim()
            binding.emailLayout.error = null

            when {
                email.isEmpty() -> {
                    binding.emailLayout.error = "Email is required"
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.emailLayout.error = "Enter a valid email"
                }

                else -> {
                    Toast.makeText(this, "OTP sent to your email", Toast.LENGTH_SHORT).show()
                    startOtpTimer()
                }
            }
        }
    }

    private fun setupVerifyButton() {
        binding.btnVerify.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val otp = binding.otpInput.text.toString().trim()

            binding.emailLayout.error = null
            binding.otpLayout.error = null

            var hasError = false

            if (email.isEmpty()) {
                binding.emailLayout.error = "Email is required"
                hasError = true
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLayout.error = "Enter a valid email"
                hasError = true
            }

            if (otp.isEmpty()) {
                binding.otpLayout.error = "OTP is required"
                hasError = true
            } else if (otp.length < 4) {
                binding.otpLayout.error = "OTP is too short"
                hasError = true
            }

            if (hasError) return@setOnClickListener

            Toast.makeText(this, "OTP verified", Toast.LENGTH_SHORT).show()
        }
    }



    private fun startOtpTimer() {
        isTimerRunning = true
        binding.tvSendOtp.isEnabled = false

        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                binding.tvSendOtp.text = "Resend in ${seconds}s"
                binding.tvSendOtp.setTextColor(Color.GRAY)
            }

            override fun onFinish() {
                isTimerRunning = false
                binding.tvSendOtp.text = "Send"
                binding.tvSendOtp.isEnabled = true
                binding.tvSendOtp.setTextColor(Color.parseColor("#0D47A1"))
            }
        }.start()
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }
}