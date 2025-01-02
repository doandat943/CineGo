package com.joverse.cinego.activity

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.joverse.cinego.databinding.ActivityTrailerBinding


class TrailerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrailerBinding
    private lateinit var player: ExoPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrailerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player

        val videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))

        player.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}