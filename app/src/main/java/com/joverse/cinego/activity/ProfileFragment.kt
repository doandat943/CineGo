package com.joverse.cinego.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.joverse.cinego.R
import com.joverse.cinego.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        initUI()

        return binding.root
    }

    private fun loadFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initUI() {
        binding.btnUpdateProfile.setOnClickListener {
            loadFragment(UpdateProfileFragment())
        }

        binding.btnUpdatePassword.setOnClickListener {
            loadFragment(UpdatePasswordFragment())
        }

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Glide.with(this).load(user.photoUrl).error(R.drawable.profile).into(binding.imgAvatar)
            binding.tvName.text = user.displayName
            binding.tvEmail.text = user.email
        }
    }

}