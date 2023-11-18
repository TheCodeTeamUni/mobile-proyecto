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
        binding.textContentAlbum.text = "Project Name"//projectDetail.nameProject
        binding.textContentDate.text = "12/12/2012"//projectDetail.startDate
        println("Aca esta imprimiendo esto: " + projectDetail.nameProject)
        binding.textContentGenre.text = "11/11/2015"//projectDetail.endDate
        binding.textContentRecord.text = "3"//projectDetail.candidates
        binding.textContentDescription.text = "Este es un proyecto de pruebas"//projectDetail.description
        //binding.listTextAspirants.text = "mmorales@crehana.com"//projectDetail.aspirants.toString()
    }

}