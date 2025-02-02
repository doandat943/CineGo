package com.joverse.cinego.activity

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.joverse.cinego.adapter.CastListAdapter
import com.joverse.cinego.adapter.CategoryEachFilmAdapter
import com.joverse.cinego.model.Movie
import com.joverse.cinego.databinding.ActivityMovieDetailBinding
import eightbitlab.com.blurview.RenderEffectBlur

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setVariable()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setVariable() {
        val item: Movie = intent.getParcelableExtra("object")!!
        val requestOptions =
            RequestOptions().transform(CenterCrop(), GranularRoundedCorners(0f, 0f, 50f, 50f))

        Glide.with(this)
            .load(item.poster)
            .apply(requestOptions)
            .into(binding.filmPic)

        binding.titleTxt.text = item.title
        binding.imdbTxt.text = "IMDB ${item.imdb}"
        binding.movieTimeTxt.text = "${item.premiere} - ${item.duration}"
        binding.movieSummeryTxt.text = item.description

        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.buyTicketBtn.setOnClickListener {
            val intent = Intent(this, SeatListActivity::class.java)
            intent.putExtra("object", item)
            startActivity(intent)
        }

        binding.imageView6.setOnClickListener {
            val intent = Intent(this, TrailerActivity::class.java)
            //intent.putExtra("object", item.trailer)
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

        item.genres.let {
            binding.genreView.adapter = CategoryEachFilmAdapter(it)
            binding.genreView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }

        item.casts.let {
            binding.castListView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.castListView.adapter = CastListAdapter(it)
        }
    }
}