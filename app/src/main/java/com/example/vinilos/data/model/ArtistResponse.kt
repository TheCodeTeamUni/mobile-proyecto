package com.example.vinilos.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

class ArtistResponse {
    @SerializedName("id")
    var id: Number = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("image")
    var image: String? = null

    @SerializedName("birthDate")
    var birthDate: Date? = null

    @SerializedName("creationDate")
    var creationDate: Date? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("albums")
    var albums: ArrayList<AlbumResponse> = ArrayList()

    @SerializedName("performerPrizes")
    var performerPrizes: ArrayList<PerformerPrizes> = ArrayList()

}