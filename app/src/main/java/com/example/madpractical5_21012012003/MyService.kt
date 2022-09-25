package com.example.madpractical5_21012012003

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder

class MyService : Service() {
    companion object {
        public val DATA_KEY = "service"
        val DATA_VALUE = "play/puse"
    }

    private lateinit var mediaPlayer: MediaPlayer
    private val audioFileArray = intArrayOf(R.raw.rrr, R.raw.kgf, R.raw.chotabheem)


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val playingIndex = intent?.getIntExtra("playingIndex", 0) ?: 0

        if (!this::mediaPlayer.isInitialized) {
            mediaPlayer = MediaPlayer.create(this, audioFileArray[playingIndex])
        }
        if (intent != null) {
            val data: String? = intent.getStringExtra("service")
            if (data == "play/pause") {
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                } else {
                    mediaPlayer.pause()
                }
            } else if (data == "next/prev") {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer = MediaPlayer.create(this, audioFileArray[playingIndex])
                mediaPlayer.start()
            }
        } else {
            mediaPlayer.start()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        super.onDestroy()
    }
}