package com.example.linkupaffinity.screens.profile

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.linkupaffinity.R
import android.widget.TextView


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnNavigate = findViewById<Button>(R.id.btnNavigate)
        val name = intent.getStringExtra("USER_NAME")
        val tvName = findViewById<TextView>(R.id.tvProfileName)
        tvName.text = name

        btnNavigate.setOnClickListener {
            // Optional: Save the bio here before leaving
            Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show()


            finish()
        }
    }
}