package com.example.vinilos.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityCompanyPortalBinding
import com.vinylsMobile.vinylsapplication.databinding.ActivityProjectModuleBinding
import com.vinylsMobile.vinylsapplication.databinding.ActivitySelectProfileBinding

class CompanyPortalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyPortalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyPortalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.projectModuleIcon.setOnClickListener {
            val intent = Intent(this, ProjectModuleActivity::class.java)
            startActivity(intent)
        }
    }
}