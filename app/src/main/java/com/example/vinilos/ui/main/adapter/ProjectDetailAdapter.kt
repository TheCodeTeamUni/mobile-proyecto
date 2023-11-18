package com.example.vinilos.ui.main.adapter


import android.content.Context
import com.bumptech.glide.Glide
import com.example.vinilos.data.model.ProjectDetailResponse
import com.example.vinilos.data.model.ProjectResponse
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailProjectBinding

class ProjectDetailAdapter(private val projectDetail: ProjectDetailResponse) {

    lateinit var context: Context

    fun adaptData(binding: ActivityDetailProjectBinding) {
        Glide.with(binding.imageViewProjectDetails.context)
            .load(R.drawable.list_project_icon)
            .into(binding.imageViewProjectDetails)
        binding.textContentAlbum.text = projectDetail.nameProject //"Project Name"//
        binding.textContentDate.text = projectDetail.startDate //"12/12/2012"//
        println("Aca esta imprimiendo esto: " + projectDetail.nameProject)
        binding.textContentGenre.text = projectDetail.endDate //"11/11/2015"//
        binding.textContentRecord.text = projectDetail.candidates
        binding.textContentDescription.text = projectDetail.description //"Este es un proyecto de pruebas"//
        binding.listTextAspirants.text = projectDetail.aspirants.toString() // "mmorales@crehana.com"
    }

}