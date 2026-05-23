package com.example.linkupaffinity.screens.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.linkupaffinity.R

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val btnApply = findViewById<Button>(R.id.btnApplyFilters)
        val container = findViewById<LinearLayout>(R.id.llFilterContainer)

        btnApply.setOnClickListener {
            val selectedInterests = mutableListOf<String>()


            for (i in 0 until container.childCount) {
                val view = container.getChildAt(i)
                if (view is CheckBox && view.isChecked) {
                    selectedInterests.add(view.text.toString())
                }
            }


            if (selectedInterests.isEmpty()) {
                Toast.makeText(this, "Please select at least one interest", Toast.LENGTH_SHORT).show()
            } else {

                val resultIntent = Intent()
                resultIntent.putStringArrayListExtra("selected_filters", ArrayList(selectedInterests))

                setResult(RESULT_OK, resultIntent)

                // Close the activity and return to Dashboard
                finish()
            }
        }
    }
}