package com.example.vinilos.data.model

import com.google.gson.annotations.SerializedName

class CandidatesResponse {
    @SerializedName("idUser")
    var idUser: Number = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("lastName")
    var lastName: String? = null

    @SerializedName("country")
    var country: String? = null

    @SerializedName("telephone")
    var telephone: String? = null

    @SerializedName("alternativeEmail")
    var alternativeEmail: String? = null

    @SerializedName("photo")
    var photo: String? = null
}