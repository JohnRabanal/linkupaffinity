package com.example.linkupaffinity.screens.login

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.linkupaffinity.R
import com.example.linkupaffinity.screens.home.DashboardActivity
import com.example.linkupaffinity.screens.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val usernameInput = findViewById<EditText>(R.id.edittextUsername)
        val passwordInput = findViewById<EditText>(R.id.edittextPassword)
        val loginCard = findViewById<CardView>(R.id.cardLogin)
        val signUpText = findViewById<TextView>(R.id.textviewSignUp)


        loginCard.setOnClickListener {
            val user = usernameInput.text.toString()
            val pass = passwordInput.text.toString()

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()

                // Navigate to Dashboard and clear back stack
                val intent = Intent(this, DashboardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. Sign Up Logic
        signUpText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }


    private fun performLogout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}