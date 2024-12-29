package com.joverse.cinego.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joverse.cinego.model.Seat
import com.joverse.cinego.R
import com.joverse.cinego.databinding.SeatItemBinding

class SeatListAdapter(
    private val seatList: List<Seat>,
    private val context: Context,
    private val selectedSeat: SelectedSeat
) : RecyclerView.Adapter<SeatListAdapter.SeatViewholder>() {
    private val selectedSeatName = ArrayList<String>()

    class SeatViewholder(val binding: SeatItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeatViewholder {
        return SeatViewholder(
            SeatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SeatViewholder, position: Int) {
        val seat = seatList[position]
        holder.binding.seat.text = seat.name

        when (seat.status) {
            Seat.SeatStatus.AVAILABLE -> {
                holder.binding.seat.setBackgroundResource(R.drawable.ic_seat_available)
                holder.binding.seat.setTextColor(context.getColor(R.color.white))
            }

            Seat.SeatStatus.SELECTED -> {
                holder.binding.seat.setBackgroundResource(R.drawable.ic_seat_selected)
                holder.binding.seat.setTextColor(context.getColor(R.color.black))
            }

            Seat.SeatStatus.UNAVAILABLE -> {
                holder.binding.seat.setBackgroundResource(R.drawable.ic_seat_unavailable)
                holder.binding.seat.setTextColor(context.getColor(R.color.grey))
            }

            null -> TODO()
        }
        holder.binding.seat.setOnClickListener {
            when (seat.status) {
                Seat.SeatStatus.AVAILABLE -> {
                    seat.status = Seat.SeatStatus.SELECTED
                    seat.name?.let { it1 -> selectedSeatName.add(it1) }
                    notifyItemChanged(position)
                }

                Seat.SeatStatus.SELECTED -> {
                    seat.status = Seat.SeatStatus.AVAILABLE
                    selectedSeatName.remove(seat.name)
                    notifyItemChanged(position)
                }

                else -> {}
            }
            val selected = selectedSeatName.joinToString(",")
            selectedSeat.Return(selected, selectedSeatName.size)
        }
    }

    override fun getItemCount(): Int = seatList.size

    interface SelectedSeat {
        fun Return(selectedName: String, num: Int)
    }
}