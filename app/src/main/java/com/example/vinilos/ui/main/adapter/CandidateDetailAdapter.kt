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
        binding.textContentCandidateName.text="Manuel Morales"//candidateDetail.name
        binding.textContentDocumentCandidate.text="809123456"//candidateDetail.document
        binding.textContentTelephoneCandidate.text="3051234567"//candidateDetail.telephone
        binding.textContentCountryCandidate.text="Colombia"//candidateDetail.country
        binding.textContentDescription.text="Esta es una descripción de prueba"//candidateDetail.description
    }
}