package com.example.vinilos.data.model

import com.google.gson.annotations.SerializedName

class ProjectResponse {

    @SerializedName("id")
    var id: Number = 0

    @SerializedName("nameProject")
    var nameProject: String? = null

    @SerializedName("startDate")
    var startDate: String? = null

    @SerializedName("endDate")
    var endDate: String? = null

    @SerializedName("candidates")
    var candidates: String? = null

    @SerializedName("description")
    var description: String? = null

}