package com.example.vinilos.data.model

import com.google.gson.annotations.SerializedName

class InterviewResultResponse {
    @SerializedName("idInterview")
    var idInterview: Number = 0

    @SerializedName("result")
    var result: String? = null

    @SerializedName("notes")
    var notes: String? = null

    @SerializedName("createdAt")
    var createdAt: String? = null
}