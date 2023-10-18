package com.example.vinilos.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id") val id: String,
    val username: String,
    val email: String,
    val type: String
)
