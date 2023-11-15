package com.example.vinilos.ui.main.adapter


import com.bumptech.glide.Glide

import com.example.vinilos.data.model.ProjectResponse
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailAlbumBinding

class ProjectDetailAdapter(private val projectDetail: ProjectResponse) {

    fun adaptData(binding: ActivityDetailAlbumBinding) {
        Glide.with(binding.imageViewAlbumDetails.context)
            .load(R.drawable.list_project_icon)
            .into(binding.imageViewAlbumDetails)
        binding.textContentAlbum.text = projectDetail.nameProject
        binding.textContentDate.text = projectDetail.startDate
        binding.textContentGenre.text = projectDetail.endDate
        binding.textContentRecord.text = projectDetail.aspirants.toString()
        binding.textContentDescription.text = projectDetail.description
    }
}