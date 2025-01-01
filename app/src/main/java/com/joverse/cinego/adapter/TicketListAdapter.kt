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
import com.joverse.cinego.activity.TicketDetailActivity
import com.joverse.cinego.databinding.ItemTicketBinding
import com.joverse.cinego.model.Ticket

class TicketListAdapter(
    private val tickets: List<Ticket>
) : RecyclerView.Adapter<TicketListAdapter.TicketViewHolder>() {

    private var context: Context? = null

    inner class TicketViewHolder(private val binding: ItemTicketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ticket: Ticket) {
            binding.movieTitle.text = ticket.movieTitle
            binding.theater.text = ticket.theater
            binding.showtime.text = ticket.date + " - " + ticket.time
            binding.totalAmount.text = ticket.totalAmount.toString()

            val requestOptions = RequestOptions()
                .transform(CenterCrop(), RoundedCorners(30))

            Glide.with(this.binding.root.context!!)
                .load(ticket.poster)
                .apply(requestOptions)
                .into(binding.moviePoster)

            binding.root.setOnClickListener {
                val intent = Intent(context, TicketDetailActivity::class.java)
                intent.putExtra("object", ticket)
                context!!.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        context = parent.context
        val binding = ItemTicketBinding.inflate(
            LayoutInflater.from(context),
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
