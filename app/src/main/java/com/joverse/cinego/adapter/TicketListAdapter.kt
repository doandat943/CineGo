package com.joverse.cinego.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joverse.cinego.databinding.ItemTicketBinding

data class Ticket(
    val movieTitle: String,
    val cinemaName: String,
    val showtime: String,
    val moviePosterResId: Int
)

class TicketAdapter(
    private val tickets: List<Ticket>,
    private val onTicketClick: (Ticket) -> Unit
) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    inner class TicketViewHolder(private val binding: ItemTicketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ticket: Ticket) {
            binding.movieTitle.text = ticket.movieTitle
            binding.cinemaName.text = ticket.cinemaName
            binding.showtime.text = ticket.showtime
            binding.moviePoster.setImageResource(ticket.moviePosterResId)

            binding.root.setOnClickListener {
                onTicketClick(ticket)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = ItemTicketBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(tickets[position])
    }

    override fun getItemCount(): Int = tickets.size
}
