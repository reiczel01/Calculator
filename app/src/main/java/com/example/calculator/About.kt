package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)
    }

    fun backButton(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
