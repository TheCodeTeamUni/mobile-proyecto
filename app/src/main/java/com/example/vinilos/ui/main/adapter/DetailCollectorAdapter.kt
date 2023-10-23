package com.example.vinilos.ui.main.adapter

import android.annotation.SuppressLint
import android.util.Log
import com.example.vinilos.data.model.CollectorResponse
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailCollectorBinding

const val IdCollector = "id"

class DetailCollectorAdapter(private val collectorDetail: CollectorResponse) {


    @SuppressLint("SetTextI18n")
    fun adaptData(binding: ActivityDetailCollectorBinding) {
        binding.textContentCollector.text = "${collectorDetail.name}"
        binding.contentFavoritePerformers.text = adaptStringFavoritePerformers(binding)
        binding.textContentComments.text = adaptStringComments(binding)


    }

    private fun adaptStringFavoritePerformers(binding: ActivityDetailCollectorBinding): String {
        val sb = StringBuilder()
        for (i in collectorDetail.favoritePerformers) {
            sb.append("- ${i.name}" + "\n")
        }
        return sb.toString()
    }

    private fun adaptStringComments(binding: ActivityDetailCollectorBinding): String {
        val sb = StringBuilder()
        Log.e("***", collectorDetail.comments.size.toString())
        for (i in collectorDetail.comments) {
            sb.append("- ${i.description}" + " ${i.rating}" + "\n")
        }
        return sb.toString()
    }

}