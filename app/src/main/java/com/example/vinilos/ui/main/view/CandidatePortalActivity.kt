package com.example.vinilos.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vinylsMobile.vinylsapplication.databinding.ActivityCandidatePortalBinding

class CandidatePortalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCandidatePortalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCandidatePortalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.aspirantProfileIcon.setOnClickListener {
            val intent = Intent(this, RegisterPersonalInformationActivity::class.java)
            startActivity(intent)
        }

        binding.employmentProfileIcon.setOnClickListener {
            val intent = Intent(this, RegisterEmploymentInformationActivity::class.java)
            startActivity(intent)
        }

        binding.professionalInformationIcon.setOnClickListener {
            val intent = Intent(this, RegisterVocationalInformationActivity::class.java)
            startActivity(intent)
        }

        binding.skillIcon.setOnClickListener {
            val intent = Intent(this, RegisterSkillInformationActivity::class.java)
            startActivity(intent)
        }
    }
}
