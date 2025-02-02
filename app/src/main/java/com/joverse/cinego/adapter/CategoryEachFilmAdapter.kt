package com.joverse.cinego.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joverse.cinego.databinding.ItemCategoryBinding

class CategoryEachFilmAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<CategoryEachFilmAdapter.Viewholder>() {

    class Viewholder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.binding.titleTxt.text = items[position]
    }

    override fun getItemCount(): Int = items.size
}