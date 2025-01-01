package com.joverse.cinego.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.joverse.cinego.adapter.DateAdapter
import com.joverse.cinego.adapter.SeatListAdapter
import com.joverse.cinego.adapter.TimeAdapter
import com.joverse.cinego.databinding.ActivitySeatListBinding
import com.joverse.cinego.model.Movie
import com.joverse.cinego.model.Seat
import com.joverse.cinego.model.ShowTime


class SeatListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeatListBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var movie: Movie
    private var price: Double = 0.0
    private var number: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentExtra()
        setVariable()
        initSeatsList()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun initSeatsList() {
        val gridLayoutManager = GridLayoutManager(this, 7)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 7 == 3) 1 else 1
            }
        }

        binding.seatRecyclerview.layoutManager = gridLayoutManager

        val dates = generateDates()
        if (dates.isNotEmpty()) {
            onDateSelected(dates.first())
        }

        binding.seatRecyclerview.isNestedScrollingEnabled = false

        binding.TimeRecyclerview.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )

        binding.dateRecyclerview.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        binding.dateRecyclerview.adapter = DateAdapter(generateDates(), ::onDateSelected)
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun getIntentExtra() {
        movie = intent.getParcelableExtra("object")!!

        val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", movie.toString())
        clipboard.setPrimaryClip(clip)
    }

    private fun generateDates(): List<String> {
        val dates = mutableListOf<String>()

        for (shwTime in movie.showDates) {
            dates.add(shwTime.date.toString())
        }
        return dates
    }

    private fun onDateSelected(selectedDate: String) {
        val selectedShowTime = movie.showDates.find { it.date == selectedDate }
        if (selectedShowTime != null) {
            val availableTimeSeats = selectedShowTime.showTimes
            val availableTimes = availableTimeSeats.map { it.time!! }
            binding.TimeRecyclerview.adapter = TimeAdapter(availableTimes, ::onTimeSelected)
            updateSeatList(availableTimeSeats.first())
        }
    }

    private fun onTimeSelected(selectedTime: String) {
        val selectedShowTime = movie.showDates
            .flatMap { it.showTimes }
            .find { it.time == selectedTime }

        if (selectedShowTime != null) {
            updateSeatList(selectedShowTime)
        }
    }

    private fun updateSeatList(selectedShowTime: ShowTime) {
        val seatList = mutableListOf<Seat>()
        val unavaiableSeatList = selectedShowTime.seats
        val totalSeats = 77
        val seatsPerRow = 7

        for (i in 1..totalSeats) {
            val row = ('A' + (i - 1) / seatsPerRow)
            val seatNumber = (i - 1) % seatsPerRow + 1
            val seatName = "$row$seatNumber"
            val seatStatus = if (unavaiableSeatList.any { it.name == seatName })
                Seat.SeatStatus.UNAVAILABLE
            else
                Seat.SeatStatus.AVAILABLE
            seatList.add(Seat(seatName, seatStatus))
        }

        val seatAdapter = SeatListAdapter(seatList, this, object : SeatListAdapter.SelectedSeat {
            override fun Return(selectedName: String, num: Int) {
                binding.numberSelectedTxt.text = "$num Seat Selected"
                val df = DecimalFormat("#.##")
                price = df.format(num * movie.price).toDouble()
                number = num
                binding.priceTxt.text = "$$price"
            }
        })
        binding.seatRecyclerview.adapter = seatAdapter
    }
}