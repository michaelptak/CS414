package com.example.sharedpreferencesexamples

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val FILE_NAME = "user_info"

    lateinit var name: EditText
    lateinit var email: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        name = findViewById<EditText>(R.id.edit_text_name)
        email = findViewById<EditText>(R.id.edit_text_email)
    }

    fun saveData(view: View){

        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)

        val editor = sharedPreferences.edit()


        editor.putString("name", name.text.toString())
        editor.putString("email", email.text.toString())

        editor.apply()
    }

    fun logData(view: View){

        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)

        val nameSaved = sharedPreferences.getString("name", "")
        val emailSaved = sharedPreferences.getString("email", "")


        name.setText("$nameSaved")
        email.setText("$emailSaved")
    }

    fun clearData(view: View){

        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.clear()

        editor.apply()

        findViewById<EditText>(R.id.edit_text_name).text.clear()
        findViewById<EditText>(R.id.edit_text_email).text.clear()
    }


}