package com.joverse.cinego.activity

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.joverse.cinego.R
import com.joverse.cinego.databinding.ActivityExportTicketBinding
import com.joverse.cinego.databinding.ActivityMovieDetailBinding
import com.joverse.cinego.databinding.ActivityTicketDetailBinding
import com.joverse.cinego.model.Ticket
import com.joverse.cinego.utils.addDurationToTime
import com.joverse.cinego.utils.formatDate
import com.joverse.cinego.utils.generateQRCode
import eightbitlab.com.blurview.RenderEffectBlur

class ExportTicketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExportTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExportTicketBinding.inflate(layoutInflater)
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

        val endShow: String = addDurationToTime(item.time, item.duration)
        val formatDate: String = formatDate(item.date)

        generateQRCode(item.toString(), binding.qrCode)

        binding.movieTitle.text = item.movieTitle
        binding.ticketCode.text = "Mã đặt vé: ${item.ticketCode}"
        binding.time.text = "Thời gian: ${item.time} - ${endShow}"
        binding.date.text = formatDate
        binding.theater.text = "Phòng chiếu: ${item.theater.toString()}"
        binding.seats.text = "Số vé: ${item.seats.size} - Ghế: ${item.seats}"


        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}