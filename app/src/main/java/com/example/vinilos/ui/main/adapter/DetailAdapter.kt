package com.example.vinilos.ui.main.adapter


import com.bumptech.glide.Glide

import com.example.vinilos.data.model.AlbumResponse
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailAlbumBinding
import java.text.DateFormat
import java.util.*

class DetailAdapter(private val albumDetail: AlbumResponse) {

    fun adaptData(binding: ActivityDetailAlbumBinding) {
        Glide.with(binding.imageViewAlbumDetails.context)
            .load(albumDetail.cover)
            .into(binding.imageViewAlbumDetails)
        binding.textContentAlbum.text = albumDetail.name
        binding.textContentDate.text = formatDate(albumDetail.releaseDate)
        binding.textContentGenre.text = albumDetail.genre
        binding.textContentRecord.text = albumDetail.recordLabel
        binding.textContentDescription.text = albumDetail.description
        binding.listTexTracks.text = adaptStringTracks(binding)
    }

    private fun adaptStringTracks(binding: ActivityDetailAlbumBinding): String {

        val sb = StringBuilder()
        for (i in albumDetail.tracks) {
            sb.append("- ${i.name} (${i.duration})" + "\n")
        }
        return sb.toString()
    }

    private fun adaptStringPerformers(binding: ActivityDetailAlbumBinding): String {

        val sb = StringBuilder()
        for (i in albumDetail.performers) {
            sb.append("- ${i.name} " + "\n")
        }
        return sb.toString()


    }

    private fun formatDate(date: Date?): String {
        return DateFormat.getDateInstance(DateFormat.LONG).format(date).toString()
    }
}