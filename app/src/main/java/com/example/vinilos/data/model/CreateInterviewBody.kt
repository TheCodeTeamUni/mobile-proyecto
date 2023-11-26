package com.example.vinilos.data.model

data class CreateInterviewBody(
    val nameCompany: String,
    val idAspirant: String,
    val nameAspirant: String,
    val lastNameAspirant: String,
    val role: String,
    val date: String,
    val notes: String
)