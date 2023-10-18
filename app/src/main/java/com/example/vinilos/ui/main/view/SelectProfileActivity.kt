package com.example.vinilos.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vinylsMobile.vinylsapplication.databinding.ActivitySelectProfileBinding

class SelectProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnLoginBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.aspirantProfileIcon.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.buildProfileIcon.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}