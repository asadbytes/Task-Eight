package com.asadbyte.taskeight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val manualButton = findViewById<Button>(R.id.btnManual)
        val retrofitButton = findViewById<Button>(R.id.btnRetrofit)
        val ktorButton = findViewById<Button>(R.id.btnKtor)

        manualButton.setOnClickListener {
            navigateToCrudScreen("Manual")
        }

        retrofitButton.setOnClickListener {
            navigateToCrudScreen("Retrofit")
        }

        ktorButton.setOnClickListener {
            navigateToCrudScreen("Ktor")
        }
    }
    private fun navigateToCrudScreen(type: String) {
        val intent = Intent(this, CrudActivity::class.java)
        intent.putExtra("clientType", type)
        startActivity(intent)
    }
}