package com.joverse.cinego.activity

import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
        // load fragment
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun initUI() {

        // Set default selection (optional)
        binding.chipNavigationBar.setItemSelected(R.id.explorer, true)

        loadFragment(ExploreFragment())


        // Handle item selection
        binding.chipNavigationBar.setOnItemSelectedListener { id ->
            when (id) {
                R.id.explorer -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, ExploreFragment())
                        .commit()
                    Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show()
                }

                R.id.favorites -> {
                    Toast.makeText(this, "Search selected", Toast.LENGTH_SHORT).show()
                }

                R.id.cart -> {
                    Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show()
                }

                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, ProfileFragment())
                        .commit()
                    Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}