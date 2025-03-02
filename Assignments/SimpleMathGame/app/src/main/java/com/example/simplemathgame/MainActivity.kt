package com.example.simplemathgame

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var equation: TextView
    private lateinit var scoreText: TextView
    private lateinit var feedback: TextView
    private lateinit var streak: TextView

    private lateinit var correctButton: Button
    private lateinit var incorrectButton: Button
    private lateinit var resetButton: Button

    private var score = 0
    private var attempts = 0
    private var currentStreak = 0
    private var correctResult = 0
    private var displayedResult = 0

    // Necessary to init globally for the functionality of feedback
    private var num1 = 0
    private var num2 = 0
    private var selectedOperator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        equation = findViewById(R.id.equation)
        scoreText = findViewById(R.id.scoreText)
        feedback = findViewById(R.id.feedback)
        streak = findViewById(R.id.streak)

        correctButton = findViewById(R.id.correctButton)
        incorrectButton = findViewById(R.id.incorrectButton)
        resetButton = findViewById(R.id.resetButton)

        correctButton.setOnClickListener { checkAnswer(true) }
        incorrectButton.setOnClickListener { checkAnswer(false) }
        resetButton.setOnClickListener { resetGame() }

        generateEquation()
        updateScore()
    }

    private fun generateEquation() {
        num1 = Random.nextInt(11, 100)
        num2 = Random.nextInt(10, num1)
        val operators = listOf("+", "-", "*")
        selectedOperator = operators.random()

        correctResult = when (selectedOperator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            else -> 0
        }

        displayedResult = correctResult // default- displayed result is correct
        val showCorrectResult = Random.nextBoolean() // 50% chance to generate an incorrect result

        if (!showCorrectResult) {
            do {
                val offset = Random.nextInt(-10,11)
                displayedResult = correctResult + offset
            } while (displayedResult == correctResult || displayedResult <= 0)
        }

        equation.text = getString(R.string.equation_text, num1, selectedOperator, num2, displayedResult)
    }

    private fun checkAnswer(userGuess: Boolean) {
        if ((displayedResult == correctResult) == userGuess) {
            score++
            currentStreak++
            feedback.text = getString(R.string.correct_message, displayedResult)
            feedback.setTextColor(Color.GREEN)

            // Check for 3 consecutive correct answers
            if (currentStreak % 3 == 0) {
                Toast.makeText(this, "You must be using a calculator or something!", Toast.LENGTH_SHORT).show()
            }

        } else {
            feedback.text = getString(R.string.incorrect_message, num1, selectedOperator, num2, correctResult)
            feedback.setTextColor(Color.RED)
            currentStreak = 0
        }
        attempts++

        updateScore()
        generateEquation()
    }

    private fun updateScore() {
        scoreText.text = getString(R.string.score_text, score, attempts)
        streak.text = getString(R.string.streak_text, currentStreak)
    }

    private fun resetGame() {
        score = 0
        attempts = 0
        currentStreak = 0
        feedback.text = ""
        updateScore()
        generateEquation()
    }
}