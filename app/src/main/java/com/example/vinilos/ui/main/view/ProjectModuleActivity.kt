package com.example.vinilos.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityProjectModuleBinding

class ProjectModuleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProjectModuleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectModuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createProjectIcon.setOnClickListener {
            val intent = Intent(this, CreateProjectActivity::class.java)
            startActivity(intent)
        }
    }
}