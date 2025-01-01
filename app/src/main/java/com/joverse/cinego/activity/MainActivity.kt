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
import com.joverse.cinego.fragment.TicketFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            //.addToBackStack(null)
            .commit()
    }

    fun initUI() {

        binding.chipNavigationBar.setItemSelected(R.id.explorer, true)
        loadFragment(ExploreFragment())

        binding.chipNavigationBar.setOnItemSelectedListener { id ->
            when (id) {
                R.id.explorer -> {
                    loadFragment(ExploreFragment())
                }

                R.id.favorites -> {
                    startActivity(Intent(this, TicketDetailActivity::class.java))
                }

                R.id.ticket -> {
                    loadFragment(TicketFragment())
                }

                R.id.profile -> {
                    loadFragment(ProfileFragment())
                }
            }
        }
    }
}