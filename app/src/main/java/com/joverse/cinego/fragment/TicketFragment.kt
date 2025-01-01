package com.joverse.cinego.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.joverse.cinego.adapter.TicketListAdapter
import com.joverse.cinego.databinding.FragmentTicketBinding
import com.joverse.cinego.model.Ticket

class TicketFragment : Fragment() {

    private lateinit var binding: FragmentTicketBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketBinding.inflate(inflater)
        database = FirebaseDatabase.getInstance()
        initUI()

        return binding.root
    }

    fun initUI() {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val myRef: DatabaseReference = database.getReference("Users").child(user.uid).child("tickets")
            val items = ArrayList<Ticket>()

            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (issue in snapshot.children) {
                            items.add(issue.getValue(Ticket::class.java)!!)
                        }
                        if (items.isNotEmpty()) {
                            binding.ticketsRecyclerView.layoutManager = LinearLayoutManager(
                                this@TicketFragment.requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            binding.ticketsRecyclerView.adapter = TicketListAdapter(items)
                        }
                        binding.progressBarTopMovies.visibility = View.GONE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }
}