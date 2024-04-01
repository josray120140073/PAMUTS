package com.example.pamuts120140073

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pamuts120140073.databinding.ActivityDetailUserBinding
import com.example.pamuts120140073.User // Pastikan import User di sini

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Setup toolbar
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail User"


        // Ambil objek User yang dikirim melalui Intent
        val user = intent.getParcelableExtra<User>("user")

        // Periksa apakah objek user tidak null
        if (user != null) {
            // Set data user ke TextView sesuai dengan atribut User
            Glide.with(this)
                .load(user.avatar)
                .placeholder(R.drawable.default_avatar)
                .into(binding.imageViewProfile)
            binding.textViewName.text = "${user.first_name} ${user.last_name}"
            binding.textViewEmail.text = user.email
            // Anda dapat menambahkan atribut lain dari User di sini sesuai kebutuhan
        } else {
            // Objek user null, berikan pesan atau tindakan yang sesuai
            binding.textViewName.text = "User not found"
            binding.textViewEmail.text = ""
            // Atau Anda bisa menampilkan pesan toast atau yang lainnya
            // Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}