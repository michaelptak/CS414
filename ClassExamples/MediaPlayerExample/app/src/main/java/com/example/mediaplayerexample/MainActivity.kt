package com.example.mediaplayerexample

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var mediaPlayer : MediaPlayer? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    fun playSong(view: View) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.song)
        }
        mediaPlayer?.start()

    }

    fun pauseSong(view: View) {
        if (mediaPlayer != null) {
            mediaPlayer?.pause()

        }
    }

    fun stopSong(view: View) {
       releaseResources()
    }

    fun releaseResources(s) {
        if (mediaPlayer != null) {
            mediaPlayer?.release()
            mediaPlayer = null

        }
    }
}