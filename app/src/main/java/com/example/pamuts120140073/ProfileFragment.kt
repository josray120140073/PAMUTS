package com.example.pamuts120140073

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pamuts120140073.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mendapatkan UID pengguna yang saat ini masuk
        val currentUserUid = firebaseAuth.currentUser?.uid

        // Mengambil data pengguna terkait dari Firestore
        if (currentUserUid != null) {
            firestore.collection("users").document(currentUserUid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Mendapatkan data pengguna
                        val user = document.data

                        // Menampilkan email
                        val email = user?.get("email").toString()
                        binding.textViewEmail.text = email

                        // Menampilkan NIK
                        val nik = user?.get("nik").toString()
                        binding.textViewNik.text = nik

                        // Menampilkan username GitHub
                        val githubUsername = user?.get("githubUsername").toString()
                        binding.textViewGithubUsername.text = githubUsername

                        // Menampilkan inisial nama pengguna pada gambar profil
                        val username = user?.get("username").toString()
                        val initial = username.firstOrNull()?.toUpperCase()
                        binding.textViewInitial.text = initial.toString()
                    } else {
                        // Dokumen tidak ditemukan atau kosong
                    }
                }
                .addOnFailureListener { exception ->
                }
        }
        binding.logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            // Navigasi kembali ke halaman login
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}