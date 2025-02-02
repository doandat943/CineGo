package com.joverse.cinego.activity

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.joverse.cinego.R
import com.joverse.cinego.databinding.ActivityTicketDetailBinding
import com.joverse.cinego.model.Ticket
import eightbitlab.com.blurview.RenderEffectBlur

class TicketDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTicketDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTicketDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setVariable()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setVariable() {
        val item: Ticket = intent.getParcelableExtra("object")!!
        val requestOptions =
            RequestOptions().transform(CenterCrop(), GranularRoundedCorners(0f, 0f, 50f, 50f))

        Glide.with(this)
            .load(item.poster)
            .apply(requestOptions)
            .into(binding.poster)

        binding.movieTitle.text = item.movieTitle
        binding.ticketCode.text = "Mã đặt vé: ${item.ticketCode}"
        binding.time.text = "${item.time} - ${item.duration}"
        binding.date.text = item.date
        binding.theater.text = item.theater.toString()
        binding.ticketCount.text = item.seats.size.toString()
        binding.seats.text = item.seats.toString()


        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.buyTicketBtn.setOnClickListener {
            val intent = Intent(this, ExportTicketActivity::class.java)
            intent.putExtra("object", item)
            startActivity(intent)
        }

        val radius = 10f
        val decorView = window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowsBackground = decorView.background

        binding.blurView.setupWith(rootView, RenderEffectBlur())
            .setFrameClearDrawable(windowsBackground)
            .setBlurRadius(radius)
        binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView.clipToOutline = true
    }
}