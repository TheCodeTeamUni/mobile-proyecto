package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiHelper
import com.google.gson.JsonObject

class ProjectRepository(private val apiHelper: ApiHelper) {

    suspend fun getProjects() = apiHelper.getProjects()
    suspend fun getProjectDetail(id: String) = apiHelper.getProjectDetail(id)

    //suspend fun postAlbumTrack(id: String, track: JsonObject) = apiHelper.postAlbumTrack(id, track)
    //suspend fun postAlbum(album: JsonObject) = apiHelper.postAlbum(album)
}