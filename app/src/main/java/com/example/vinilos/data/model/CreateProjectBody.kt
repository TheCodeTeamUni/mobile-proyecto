package com.example.vinilos.data.model

data class CreateProjectBody(
    val nameProject: String,
    val startDate: String,
    val endDate: String,
    val description: String,
    val aspirants: Int
)
