package com.example.activitylifecyclesdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myButton: Button = findViewById(R.id.openSecond)

        myButton.setOnClickListener { view ->
            openSecondActivity(view)
        }

        Log.d(TAG, "onCreate was called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart was called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume was called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause was called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop was called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy was called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart was called")
    }

    fun openSecondActivity(view: View) {
        val myIntent = Intent(this, SecondActivity::class.java)
        startActivity(myIntent)
    }

    fun showInfo (view: View) {
        val textUserTyped = findViewById<EditText>(R.id.editTextText).text
        findViewById<TextView>(R.id.tv_info).text = "$textUserTyped"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState was called ")
        val info = findViewById<TextView>(R.id.tv_info).text.toString()

        outState.putString("info", info)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState was called ")

        val info = savedInstanceState?.getString("info")
        findViewById<TextView>(R.id.tv_info).text = "$info"
    }
}