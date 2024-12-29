package com.joverse.cinego.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.joverse.cinego.R
import com.joverse.cinego.databinding.ItemDateBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateAdapter(private val timeSlots: List<String>, private val onDateSelected: (String) -> Unit) :
    RecyclerView.Adapter<DateAdapter.TimeViewholder>() {
    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    init {
        if (timeSlots.isNotEmpty()) {
            selectedPosition = 0
        }
    }

    inner class TimeViewholder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(date: String) {
            val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val date2 = LocalDate.parse(date, inputFormatter)
            val outputFormatter = DateTimeFormatter.ofPattern("EEE/dd/MMM")
            val dateString = outputFormatter.format(date2)

            val dateParts = dateString.split("/")
            if (dateParts.size == 3) {
                binding.dayTxt.text = dateParts[0]
                binding.datMonthTxt.text = dateParts[1] + " " + dateParts[2]

                if (selectedPosition == position) {
                    binding.mailLayout.setBackgroundResource(R.drawable.white_bg)
                    binding.dayTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                    binding.datMonthTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                } else {
                    binding.mailLayout.setBackgroundResource(R.drawable.light_black_bg)
                    binding.dayTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    binding.datMonthTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                }

                binding.root.setOnClickListener {
                    val position = position
                    if (position != RecyclerView.NO_POSITION) {
                        lastSelectedPosition = selectedPosition
                        selectedPosition = position
                        notifyItemChanged(lastSelectedPosition)
                        notifyItemChanged(selectedPosition)
                        onDateSelected(date)
                        Log.d("DateAdapter", "onDateSelected: $date")
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateAdapter.TimeViewholder {
        return TimeViewholder(
            ItemDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DateAdapter.TimeViewholder, position: Int) {
        holder.bind(timeSlots[position])
    }

    override fun getItemCount(): Int = timeSlots.size
}