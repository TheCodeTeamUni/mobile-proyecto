package com.example.vinilos.data.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class TracksResponse() {
    @SerializedName("id")
    var id: Number = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("duration")
    var duration: String? = null

}
