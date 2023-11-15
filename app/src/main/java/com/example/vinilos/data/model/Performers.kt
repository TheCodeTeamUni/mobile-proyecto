package com.example.vinilos.data.model

import java.io.Serializable

data class Performer(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
    val albums: List<ProjectResponse>
) : Serializable