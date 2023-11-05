package com.example.vinilos.data.model

data class RegisterCandidateEducationInformationBody(
    val typeEducation: String,
    val level: String,
    val title: String,
    val grade: Boolean,
    val institution: String,
    val startDate: String,
    val endDate: String
)
