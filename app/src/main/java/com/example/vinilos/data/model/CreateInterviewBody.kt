package com.example.vinilos.data.model

data class CreateInterviewBody(
    val nameProject: String,
    val startDate: String,
    val endDate: String,
    val description: String,
    val aspirants: Int
)