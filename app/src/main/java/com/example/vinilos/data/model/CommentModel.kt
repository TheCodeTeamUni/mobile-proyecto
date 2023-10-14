package com.example.vinilos.data.model

import com.google.gson.annotations.SerializedName

class CommentModel {
    @SerializedName("id")
    lateinit var id: Number

    @SerializedName("description")
    var description: String? = null

    @SerializedName("rating")
    lateinit var rating: Number
}