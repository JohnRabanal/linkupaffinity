package com.example.linkupaffinity.screens.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.linkupaffinity.R
import com.example.linkupaffinity.screens.login.LoginActivity
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Toast
import com.example.linkupaffinity.screens.profile.ProfileActivity


class DashboardActivity : AppCompatActivity() {

    private val filterLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedFilters = result.data?.getStringArrayListExtra("selected_filters")
            Toast.makeText(this, "Filters received: $selectedFilters", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Bind Main Views
        val cardMatches = findViewById<CardView>(R.id.cardMatches)
        val badgeMatches = findViewById<View>(R.id.badgeMatches)
        val btnLogout = findViewById<ImageView>(R.id.btnLogout)
        val cardSearch = findViewById<CardView>(R.id.cardSearchAffinity)
        val badgeSearch = findViewById<View>(R.id.badgeSearch)
        val tvWelcome = findViewById<TextView>(R.id.tvWelcomeUser)

        btnLogout.setOnClickListener {
            performLogout()
        }

        tvWelcome.text = getString(R.string.welcome_message, "John")

        // Search Click Listener
        cardSearch.setOnClickListener {
            badgeSearch.visibility = View.GONE
            filterLauncher.launch(Intent(this, SearchActivity::class.java))
        }

        // Matches Click Listener
        cardMatches.setOnClickListener {
            badgeMatches.visibility = View.GONE
            Toast.makeText(this, "No matches found", Toast.LENGTH_SHORT).show()
        }

        btnLogout.setOnClickListener { performLogout() }

        // --- Bottom Navigation Logic (NOW INSIDE ONCREATE) ---
        findViewById<ImageView>(R.id.navProfile).setOnClickListener {
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageView>(R.id.navEvents).setOnClickListener {
            Toast.makeText(this, "Events clicked", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageView>(R.id.navMessage).setOnClickListener {
            Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageView>(R.id.navSettings).setOnClickListener {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
        }

        // Inside onCreate in DashboardActivity.kt
        findViewById<ImageView>(R.id.navProfile).setOnClickListener {
            // Explicitly using the class name and correct context
            val intent = Intent(this, ProfileActivity::class.java)
            // Sending the name to the next activity
            intent.putExtra("USER_NAME", "John")
            startActivity(intent)
        }
    }

    private fun performLogout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}