package com.example.vinilos.ui.main.adapter


import com.bumptech.glide.Glide
import com.example.vinilos.data.model.InterviewResponse
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailInterviewBinding

class InterviewDetailAdapter(private val interviewDetail: InterviewResponse) {

    fun adaptData(binding: ActivityDetailInterviewBinding) {
        Glide.with(binding.imageViewInterviewDetails.context)
            .load(R.drawable.list_interviews_icon)
            .into(binding.imageViewInterviewDetails)
        binding.textContentCompanyName.text = interviewDetail.nameCompany
        binding.textContentCandidateName.text = interviewDetail.nameAspirant
        binding.textContentCandidateLastName.text = interviewDetail.lastNameAspirant
        binding.textContentRol.text = interviewDetail.role
        binding.textContentInterviewDate.text = interviewDetail.date
    }
}