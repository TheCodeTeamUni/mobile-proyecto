package com.example.vinilos.ui.main.adapter


import com.bumptech.glide.Glide

import com.example.vinilos.data.model.AlbumResponse
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailAlbumBinding
import java.text.DateFormat
import java.util.*

class DetailAdapter(private val albumDetail: AlbumResponse) {

    fun adaptData(binding: ActivityDetailAlbumBinding) {
        Glide.with(binding.imageViewAlbumDetails.context)
            .load(R.drawable.list_project_icon)
            .into(binding.imageViewAlbumDetails)
        binding.textContentAlbum.text = albumDetail.nameProject
        binding.textContentDate.text = albumDetail.startDate
        binding.textContentGenre.text = albumDetail.endDate
        binding.textContentRecord.text = albumDetail.aspirants.toString()
        binding.textContentDescription.text = albumDetail.description
    }
}