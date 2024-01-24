package com.example.calculator

import com.example.calculator.AdvancedCalculator
import com.example.calculator.About
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.calculator.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn_simple).setOnClickListener {
            val intent = Intent(this, SimpleCalculator::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.btn_advanced).setOnClickListener {
            val intent = Intent(this, AdvancedCalculator::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.btn_about).setOnClickListener {
            val intent = Intent(this, About::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.btn_exit).setOnClickListener {
            finishAffinity()
            System.exit(0)
        }
    }
}