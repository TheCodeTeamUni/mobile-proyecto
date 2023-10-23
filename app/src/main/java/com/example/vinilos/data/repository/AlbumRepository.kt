package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiHelper
import com.google.gson.JsonObject

class AlbumRepository(private val apiHelper: ApiHelper) {

    suspend fun getAlbums() = apiHelper.getAlbums()

    suspend fun getAlbumDetail(id: String) = apiHelper.getAlbumDetail(id)

    suspend fun postAlbumTrack(id: String, track: JsonObject) = apiHelper.postAlbumTrack(id, track)

    suspend fun postAlbum(album: JsonObject) = apiHelper.postAlbum(album)
}