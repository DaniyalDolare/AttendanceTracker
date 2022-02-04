package com.example.attendancetracker

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        val setupBtn = findViewById<TextView>(R.id.setupBtn)
        setupBtn.setOnClickListener {
            val intent = Intent(this,SetupTimeTable::class.java)
            startActivity(intent)
        }
    }
}