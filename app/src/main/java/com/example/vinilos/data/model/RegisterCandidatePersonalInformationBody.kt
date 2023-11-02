package com.example.vinilos.data.model

data class RegisterCandidatePersonalInformationBody(
    val name: String,
    val lastName: String,
    val typeDocument: String,
    val document: String,
    val gender: String,
    val alternativeEmail: String,
    val telephone: String,
    val country: String,
    val address: String,
    val birthdate: String,
    val description: String,
    val photo: String
)
