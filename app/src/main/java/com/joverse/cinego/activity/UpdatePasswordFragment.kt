package com.joverse.cinego.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.auth
import com.joverse.cinego.databinding.FragmentUpdatePasswordBinding

class UpdatePasswordFragment : Fragment() {

    private lateinit var binding: FragmentUpdatePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false)

        binding.btnSubmit.setOnClickListener {
            val user = Firebase.auth.currentUser
            if (user != null) {

                val credential: AuthCredential =
                    EmailAuthProvider.getCredential(user.email.toString(), binding.edtOldPassword.text.toString())

                user.reauthenticate(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.updatePassword(binding.edtNewPassword.text.toString()).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(requireContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
                                parentFragmentManager.popBackStack();
                            }
                        }
                    }
                }

            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}