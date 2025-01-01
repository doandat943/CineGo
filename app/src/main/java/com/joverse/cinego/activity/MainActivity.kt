package com.joverse.cinego.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.joverse.cinego.R
import com.joverse.cinego.databinding.ActivityMainBinding
import com.joverse.cinego.fragment.ExploreFragment
import com.joverse.cinego.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initUI()
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            //.addToBackStack(null)
            .commit()
    }

    fun initUI() {

        // Set default selection (optional)
        binding.chipNavigationBar.setItemSelected(R.id.explorer, true)

        loadFragment(ExploreFragment())

        // Handle item selection
        binding.chipNavigationBar.setOnItemSelectedListener { id ->
            when (id) {
                R.id.explorer -> {
                    loadFragment(ExploreFragment())
                }

                R.id.favorites -> {
                    startActivity(Intent(this, TicketDetailActivity::class.java))
                }

                R.id.ticket -> {
                    loadFragment(ExploreFragment())
                }

                R.id.profile -> {
                    loadFragment(ProfileFragment())
                }
            }
        }
    }
}