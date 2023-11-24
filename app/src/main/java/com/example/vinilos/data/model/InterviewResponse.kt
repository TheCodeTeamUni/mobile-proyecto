package com.example.vinilos.data.model

import com.google.gson.annotations.SerializedName

class InterviewResponse {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("idAspirant")
    var idAspirant: String? = null

    @SerializedName("nameAspirant")
    var nameAspirant: String? = null

    @SerializedName("lastNameAspirant")
    var lastNameAspirant: String? = null

    @SerializedName("role")
    var role: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("notes")
    var notes: String? = null
}