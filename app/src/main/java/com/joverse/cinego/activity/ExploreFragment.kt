package com.joverse.cinego.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.*
import com.joverse.cinego.adapter.FilmListAdapter
import com.joverse.cinego.adapter.SliderAdapter
import com.joverse.cinego.databinding.FragmentExploreBinding
import com.joverse.cinego.model.Film
import com.joverse.cinego.model.SliderItems

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()

        initUI()
        initBanner()
        initTopMoving()
        initUpcomming()

        return binding.getRoot()
    }

    private fun initUI() {
        binding.btnSignout.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(requireContext(), IntroActivity::class.java))
        }
        val user = Firebase.auth.currentUser
        if (user != null) {
            // Glide.with(this).load(user.photoUrl).error(R.drawable.profile).into(binding.imgAvatar)
            binding.tvName.text = user.displayName
            binding.tvEmail.text = user.email
            binding.edtSearch.setText(user.uid)
        }
    }

    private fun initBanner() {
        val myRef: DatabaseReference = database.getReference("Banners")
        binding.progressBarSlider.visibility = View.VISIBLE

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderItems>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderItems::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                binding.progressBarSlider.visibility = View.GONE
                banners(lists)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }


    private fun initTopMoving() {
        val myRef: DatabaseReference = database.getReference("Items")
        binding.progressBarTopMovies.visibility = View.VISIBLE
        val items = ArrayList<Film>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        items.add(issue.getValue(Film::class.java)!!)
                    }
                    if (items.isNotEmpty()) {
                        binding.recyclerViewTopMovies.layoutManager = LinearLayoutManager(
                            this@ExploreFragment.requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.recyclerViewTopMovies.adapter = FilmListAdapter(items)
                    }
                    binding.progressBarTopMovies.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun initUpcomming() {
        val myRef: DatabaseReference = database.getReference("Upcomming")
        binding.progressBarupcomming.visibility = View.VISIBLE
        val items = ArrayList<Film>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        items.add(issue.getValue(Film::class.java)!!)
                    }
                    if (items.isNotEmpty()) {
                        binding.recyclerViewUpcomming.layoutManager = LinearLayoutManager(
                            this@ExploreFragment.requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.recyclerViewUpcomming.adapter = FilmListAdapter(items)
                    }
                    binding.progressBarupcomming.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private val sliderHandler = Handler()
    private val sliderRunnable = Runnable {
        binding.viewPager2.currentItem = binding.viewPager2.currentItem + 1
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
    }

    private fun banners(lists: MutableList<SliderItems>) {
        binding.viewPager2.adapter = SliderAdapter(lists, binding.viewPager2)
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.offscreenPageLimit = 3
        binding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        }

        binding.viewPager2.setPageTransformer(compositePageTransformer)
        binding.viewPager2.currentItem = 1
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
            }
        })
    }
}