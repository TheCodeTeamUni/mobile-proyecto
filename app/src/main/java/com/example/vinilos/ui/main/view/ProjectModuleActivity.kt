package com.example.vinilos.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        binding.listProjectsIcon.setOnClickListener {
            val intent = Intent(this, ListProjectActivity::class.java)
            startActivity(intent)
        }
    }
}