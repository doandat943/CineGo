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
import com.joverse.cinego.activity.MovieDetailActivity
import com.joverse.cinego.model.Movie
import com.joverse.cinego.databinding.ItemMovieBinding

class MovieListAdapter(
    private val items: ArrayList<Movie>
) : RecyclerView.Adapter<MovieListAdapter.FilmViewHolder>() {

    private var context: Context? = null

    inner class FilmViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.nameTxt.text = movie.title
            val requestOptions = RequestOptions()
                .transform(CenterCrop(), RoundedCorners(30))

            Glide.with(context!!)
                .load(movie.poster)
                .apply(requestOptions)
                .into(binding.pic)

            binding.root.setOnClickListener {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("object", movie)
                context!!.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapter.FilmViewHolder {
        context = parent.context
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieListAdapter.FilmViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}