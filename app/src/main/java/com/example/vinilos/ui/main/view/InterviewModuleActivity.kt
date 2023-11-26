package com.example.vinilos.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vinylsMobile.vinylsapplication.databinding.ActivityInterviewModuleBinding

class InterviewModuleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInterviewModuleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterviewModuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createInterviewIcon.setOnClickListener {
            val intent = Intent(this, CreateInterviewActivity::class.java)
            startActivity(intent)
        }

        binding.listInterviewIcon.setOnClickListener {
            val intent = Intent(this, ListInterviewActivity::class.java)
            startActivity(intent)
        }
    }
}