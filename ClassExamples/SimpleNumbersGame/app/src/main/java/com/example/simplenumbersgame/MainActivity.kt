package com.example.simplenumbersgame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Random

class MainActivity : AppCompatActivity() {
    private var points = 0

    // onCreate function is executed as soon as the app is launched
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.equal_button).setOnClickListener {

        }

        setRandomNumbers()
    }

    fun checkCorrect(view: View) {
        val number1 = findViewById<TextView>(R.id.random_number1).text.toString().toInt()
        val number2 = findViewById<TextView>(R.id.random_number2).text.toString().toInt()
        val message: String

        if ((view.id == R.id.greater_than_button && number1 > number2) ||
            (view.id == R.id.equal_button && number1 == number2) ||
            (view.id == R.id.less_than_button && number1 < number2)){
            points++
            message = "Yay!, you got it!"
        } else {
            points--
            message = "Are you high?"
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        findViewById<TextView>(R.id.score).text = "Score: $points"

        setRandomNumbers()
    }
    // Generates the random numbers
    private fun setRandomNumbers() {
        findViewById<TextView>(R.id.random_number1).text = "${generateRandomNumber(10)}"
        findViewById<TextView>(R.id.random_number2).text = "${generateRandomNumber(10)}"
    }

    private fun generateRandomNumber(range: Int): Int {{{}}
        return Random().nextInt(10)
    }
}