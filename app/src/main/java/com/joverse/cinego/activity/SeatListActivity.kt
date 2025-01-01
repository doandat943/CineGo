package com.joverse.cinego.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.joverse.cinego.adapter.DateAdapter
import com.joverse.cinego.adapter.SeatListAdapter
import com.joverse.cinego.adapter.TimeAdapter
import com.joverse.cinego.databinding.ActivitySeatListBinding
import com.joverse.cinego.model.*
import com.joverse.cinego.utils.generateRandomCode


class SeatListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeatListBinding
    private lateinit var database: FirebaseDatabase
    private var fbUser: User? = null

    private lateinit var movie: Movie
    private var theater: String? = null
    private var date: String? = null
    private var time: String? = null
    private var totalAmount: Int = 0
    private var selectedSeatList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeatListBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance()
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

        val dates = generateDates()
        if (dates.isNotEmpty()) {
            onDateSelected(dates.first())
        }

        binding.seatRecyclerview.layoutManager = gridLayoutManager
        binding.seatRecyclerview.isNestedScrollingEnabled = false

        binding.TimeRecyclerview.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.dateRecyclerview.layoutManager = LinearLayoutManager(
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
        binding.button.setOnClickListener {
            bookTicket()
        }
    }

    private fun bookTicket() {
        val ticket: Ticket = Ticket(
            generateRandomCode(),
            Ticket.TicketStatus.AVAILABLE,
            movie.title,
            movie.poster,
            theater,
            date,
            time,
            movie.duration,
            totalAmount,
            selectedSeatList
        )
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {

            val myRef: DatabaseReference = database.getReference("Users").child(user.uid)

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    fbUser = dataSnapshot.getValue(User::class.java) ?: User()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

            fbUser.let {
                if (it != null) {
                    it.balance -= totalAmount
                    it.tickets += ticket

                    myRef.setValue(it).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Đã đặt vé thành công", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
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
        date = selectedDate

        val selectedShowTime = movie.showDates.find { it.date == selectedDate }
        if (selectedShowTime != null) {
            val availableTimeSeats = selectedShowTime.showTimes
            val availableTimes = availableTimeSeats.map { it.time!! }
            binding.TimeRecyclerview.adapter = TimeAdapter(availableTimes, ::onTimeSelected)
            availableTimeSeats.first().time?.let { onTimeSelected(it) }
        }
    }

    private fun onTimeSelected(selectedTime: String) {
        time = selectedTime

        val selectedShowTime = movie.showDates
            .flatMap { it.showTimes }
            .find { it.time == selectedTime }

        if (selectedShowTime != null) {
            Toast.makeText(this, "${selectedShowTime.theater}", Toast.LENGTH_SHORT).show()
            theater = selectedShowTime.theater
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
            override fun Return(selectedSeatName: ArrayList<String>) {
                totalAmount = selectedSeatName.size * movie.price
                selectedSeatList = selectedSeatName

                binding.numberSelectedTxt.text = "${selectedSeatName.size} Seat Selected"
                binding.priceTxt.text = "$$totalAmount"
            }
        })
        binding.seatRecyclerview.adapter = seatAdapter
    }
}