package com.example.widgets

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.showInfo).setOnClickListener {
            val name = findViewById<EditText>(R.id.nameInput).text
            val age = findViewById<EditText>(R.id.ageInput).text

            findViewById<TextView>(R.id.feedback).text = "Hello, $name,  your age is ${age.toString().toInt()}"

            name.clear()
            age.clear()
        }

        fun radioButtonClick(view: View) {
//            if (view.id == R.id.male) {
//                Toast.makeText(this, "Male is selected", Toast.LENGTH_SHORT).show()
//            } else if (view.id == R.id.female) {
//                Toast.makeText(this, "Female is selected", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Other is selected", Toast.LENGTH_SHORT).show()
//            }

            val message= when(view.id) {
                R.id.male -> "Male is selected"
                R.id.female -> "Female is selected"
                else -> "Other is selected"
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

    }
}