package com.example.vinilos.data.model

data class RegisterCandidateWorkExperienceInformationBody(
    val company: String,
    val position: String,
    val actualJob: Boolean,
    val startDate: String,
    val endDate: String
)
