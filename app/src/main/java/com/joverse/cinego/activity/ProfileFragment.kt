package com.joverse.cinego.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
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

    private fun initUI() {
//        binding.btnSignout.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            startActivity(Intent(requireContext(), IntroActivity::class.java))
//        }

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Glide.with(this).load(user.photoUrl).error(R.drawable.profile).into(binding.imgAvatar)
            binding.tvName.text = user.displayName
            binding.tvEmail.text = user.email
        }
    }

}