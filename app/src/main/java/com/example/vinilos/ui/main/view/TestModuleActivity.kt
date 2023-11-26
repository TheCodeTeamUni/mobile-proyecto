package com.example.vinilos.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityProjectModuleBinding
import com.vinylsMobile.vinylsapplication.databinding.ActivityTestModuleBinding

class TestModuleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestModuleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestModuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createPerformanceEvaluationIcon.setOnClickListener {
            val intent = Intent(this, CreatePerformanceEvaluationActivity::class.java)
            startActivity(intent)
        }

        binding.registerResultTestIcon.setOnClickListener {
            val intent = Intent(this, CreateResultTechnicalTestActivity::class.java)
            startActivity(intent)
        }
    }
}