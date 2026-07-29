package com.example.volumewake

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusText = findViewById<TextView>(R.id.statusText)
        val enableButton = findViewById<Button>(R.id.enableButton)

        statusText.text =
            "Egiye jete hobe:\n" +
            "1. 'Open Accessibility Settings' e click koro\n" +
            "2. List e 'Volume Wake' khuje ber koro\n" +
            "3. Seta ON koro\n\n" +
            "Erpor screen off obosthay Volume Down chapleyi screen on hobe."

        enableButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
    }
}
