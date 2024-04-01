package com.example.pamuts120140073

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pamuts120140073.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            performRegister()
        }
    }

    private fun performRegister() {
        val email = binding.EtEmail.text.toString()
        val password = binding.EtPass.text.toString()
        val username = binding.EtUsername.text.toString()
        val nik = binding.EtNik.text.toString()
        val githubUsername = binding.EtGithub.text.toString()

        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || nik.isEmpty() || githubUsername.isEmpty()) {
            showToast("All fields are required")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    currentUser?.let { user ->
                        // Registrasi berhasil, simpan data tambahan pengguna ke Firestore
                        val userData = hashMapOf(
                            "email" to email,
                            "username" to username,
                            "nik" to nik,
                            "githubUsername" to githubUsername
                        )
                        FirebaseFirestore.getInstance().collection("users")
                            .document(user.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                showToast("Registration successful")
                                navigateToHome()
                            }
                            .addOnFailureListener { e ->
                                showToast("Failed to register: ${e.message}")
                            }
                    } ?: run {
                        showToast("Failed to retrieve current user")
                    }
                } else {
                    // Registrasi gagal
                    val exception = task.exception
                    showToast("Registration failed: ${exception?.message}")
                }
            }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeFragment::class.java))
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}