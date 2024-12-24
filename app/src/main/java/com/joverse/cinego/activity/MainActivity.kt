package com.joverse.cinego.activity

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.joverse.cinego.R
import com.joverse.cinego.databinding.ActivityMainBinding

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
                    Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show()
                }

                R.id.favorites -> {
                    loadFragment(ExploreFragment())
                    Toast.makeText(this, "Search selected", Toast.LENGTH_SHORT).show()
                }

                R.id.cart -> {
                    loadFragment(ExploreFragment())
                    Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show()
                }

                R.id.profile -> {
                    loadFragment(ProfileFragment())
                    Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}