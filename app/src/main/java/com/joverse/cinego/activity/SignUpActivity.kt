package com.joverse.cinego.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.joverse.cinego.R

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initUI()
    }



    fun initUI() {
        val edtName: EditText = findViewById(R.id.edtName)
        val edtEmail: EditText = findViewById(R.id.edtEmail)
        val edtPassword: EditText = findViewById(R.id.edtPassword)
        val btnSignUp: Button = findViewById(R.id.btnSignUp)
        val btnSignIn: ImageButton = findViewById(R.id.btnSignIn)

        btnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)

                        val user = Firebase.auth.currentUser

                        val profileUpdates = userProfileChangeRequest {
                            displayName = edtName.text.toString()
                            photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")

                        }

                        user!!.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Lỗi: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }


        }

        btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}