package com.example.vinilos.ui.main.adapter


import android.content.Context
import android.text.method.TextKeyListener.clear
import com.bumptech.glide.Glide

import com.example.vinilos.data.model.ProjectResponse
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailProjectBinding
import java.text.DateFormat
import java.util.*
import java.util.Collections.addAll

class ProjectDetailAdapter(private val projectDetail: ProjectResponse) {

    lateinit var context: Context

    fun adaptData(binding: ActivityDetailProjectBinding) {
        Glide.with(binding.imageViewProjectDetails.context)
            .load(R.drawable.list_project_icon)
            .into(binding.imageViewProjectDetails)
        binding.textContentAlbum.text = projectDetail.nameProject
        binding.textContentDate.text = projectDetail.startDate
        println("Aca esta imprimiendo esto: " + projectDetail.nameProject)
        binding.textContentGenre.text = projectDetail.endDate
        binding.textContentRecord.text = projectDetail.candidates
        binding.textContentDescription.text = projectDetail.description
    }


}