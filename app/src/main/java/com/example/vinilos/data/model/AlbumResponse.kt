package com.example.vinilos.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

class AlbumResponse {
    @SerializedName("id")
    var id: Number = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("cover")
    var cover: String? = null

    @SerializedName("releaseDate")
    var releaseDate = Date()

    @SerializedName("description")
    var description: String? = null

    @SerializedName("genre")
    var genre: String? = null

    @SerializedName("recordLabel")
    var recordLabel: String? = null

    @SerializedName("performers")
    var performers: ArrayList<ArtistResponse> = ArrayList()

    @SerializedName("tracks")
    var tracks: ArrayList<TracksResponse> = ArrayList()
}