package com.example.linkupaffinity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.linkupaffinity.screens.login.LoginActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Directly route the application window root to open your completed Login Activity screen automatically
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}