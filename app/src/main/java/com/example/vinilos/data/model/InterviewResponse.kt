package com.example.vinilos.data.model

import com.google.gson.annotations.SerializedName

class InterviewResponse {
    @SerializedName("nameProject")
    var nameProject: String? = null

    @SerializedName("startDate")
    var startDate: String? = null

    @SerializedName("endDate")
    var endDate: String? = null

    @SerializedName("aspirants")
    var aspirants: Number = 0

    @SerializedName("description")
    var description: String? = null
}