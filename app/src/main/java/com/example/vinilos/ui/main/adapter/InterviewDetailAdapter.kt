package com.example.vinilos.ui.main.adapter


import com.bumptech.glide.Glide
import com.example.vinilos.data.model.InterviewResponse
import com.example.vinilos.data.model.InterviewResultResponse
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailInterviewBinding

class InterviewDetailAdapter(private val interviewDetail: InterviewResultResponse) {

    fun adaptData(binding: ActivityDetailInterviewBinding) {
        Glide.with(binding.imageViewInterviewDetails.context)
            .load(R.drawable.list_interviews_icon)
            .into(binding.imageViewInterviewDetails)
        binding.textContentInterviewResult.text = interviewDetail.result
        println("esto imprime esta chorrada: " + interviewDetail.result)
        binding.textContentInterviewNotes.text = interviewDetail.notes
        binding.textContentInterviewDate.text = interviewDetail.createdAt
    }
}