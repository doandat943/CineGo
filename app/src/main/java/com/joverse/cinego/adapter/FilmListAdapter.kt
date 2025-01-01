package com.joverse.cinego.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.joverse.cinego.activity.FilmDetailActivity
import com.joverse.cinego.model.Film
import com.joverse.cinego.databinding.ItemFilmBinding

class FilmListAdapter(private val items: ArrayList<Film>) : RecyclerView.Adapter<FilmListAdapter.FilmViewHolder>() {
    private var context: Context? = null

    inner class FilmViewHolder(private val binding: ItemFilmBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(film: Film) {
            binding.nameTxt.text = film.title
            val requestOptions = RequestOptions()
                .transform(CenterCrop(), RoundedCorners(30))

            Glide.with(context!!)
                .load(film.poster)
                .apply(requestOptions)
                .into(binding.pic)

            binding.root.setOnClickListener {
                val intent = Intent(context, FilmDetailActivity::class.java)
                intent.putExtra("object", film)
                context!!.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListAdapter.FilmViewHolder {
        context = parent.context
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmListAdapter.FilmViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}