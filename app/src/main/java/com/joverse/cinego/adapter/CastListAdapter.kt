package com.joverse.cinego.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joverse.cinego.model.Cast
import com.joverse.cinego.databinding.ItemCastBinding

class CastListAdapter(private val cast: ArrayList<Cast>) :
    RecyclerView.Adapter<CastListAdapter.CastViewHolder>() {
    private var context: Context? = null

    inner class CastViewHolder(private val binding: ItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            context?.let {
                Glide.with(it)
                    .load(cast.picUrl)
                    .into(binding.actorImage)
            }
            binding.nameTxt.text = cast.actor
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastListAdapter.CastViewHolder {
        context=parent.context
        val binding =
            ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastListAdapter.CastViewHolder, position: Int) {
        holder.bind(cast[position])
    }

    override fun getItemCount(): Int {
        return cast.size
    }
}