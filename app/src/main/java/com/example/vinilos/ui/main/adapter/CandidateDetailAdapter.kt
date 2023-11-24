package com.example.vinilos.ui.main.adapter

import com.bumptech.glide.Glide
import com.example.vinilos.data.model.CandidateDetailResponse
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailCandidateBinding

class CandidateDetailAdapter(private val candidateDetail: CandidateDetailResponse) {

    fun adaptData(binding: ActivityDetailCandidateBinding) {
        Glide.with(binding.imageViewProjectDetails.context)
            .load((R.drawable.icon_profile_candidate))
            //.load(candidateDetail.photo)
            .into(binding.imageViewProjectDetails)
        binding.textContentCandidateName.text=candidateDetail.name
        println("esto imprime esta chorrada: " + candidateDetail.name)
        binding.textContentDocumentCandidate.text=candidateDetail.document
        binding.textContentTelephoneCandidate.text=candidateDetail.telephone
        binding.textContentCountryCandidate.text=candidateDetail.country
        binding.textContentDescription.text=candidateDetail.description
    }
}