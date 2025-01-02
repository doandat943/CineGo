package com.joverse.cinego.fragment

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
import com.joverse.cinego.activity.IntroActivity
import com.joverse.cinego.adapter.MovieListAdapter
import com.joverse.cinego.adapter.SliderAdapter
import com.joverse.cinego.databinding.FragmentExploreBinding
import com.joverse.cinego.model.Movie
import com.joverse.cinego.model.SliderItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs

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
        initMovieList()

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
                val lists = mutableListOf<SliderItem>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderItem::class.java)
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

    private fun initMovieList() {
        val myRef: DatabaseReference = database.getReference("Movies")
        binding.progressBarupcomming.visibility = View.VISIBLE
        val showingList = ArrayList<Movie>()
        val upcomingList = ArrayList<Movie>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val current = LocalDate.now()
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val today = current.format(formatter)

                    for (issue in snapshot.children) {
                        val movie = issue.getValue(Movie::class.java)
                        movie?.let {
                            try {
                                if (it.premiere != null) {
                                    if (it.premiere!! <= today) {
                                        showingList.add(it)
                                    } else {
                                        upcomingList.add(it)
                                    }
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    if (showingList.isNotEmpty()) {
                        binding.recyclerViewTopMovies.layoutManager = LinearLayoutManager(
                            this@ExploreFragment.requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.recyclerViewTopMovies.adapter = MovieListAdapter(showingList)
                    }
                    binding.progressBarTopMovies.visibility = View.GONE

                    if (upcomingList.isNotEmpty()) {
                        binding.recyclerViewUpcomming.layoutManager = LinearLayoutManager(
                            this@ExploreFragment.requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.recyclerViewUpcomming.adapter = MovieListAdapter(upcomingList)
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
        binding.viewPager2.currentItem += 1
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
    }

    private fun banners(lists: MutableList<SliderItem>) {
        binding.viewPager2.adapter = SliderAdapter(lists, binding.viewPager2)
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.offscreenPageLimit = 3
        binding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - abs(position)
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