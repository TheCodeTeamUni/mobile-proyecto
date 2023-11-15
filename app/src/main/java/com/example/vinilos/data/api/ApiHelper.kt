package com.example.vinilos.data.api

import com.google.gson.JsonObject

class ApiHelper(private val apiService: ApiService) {

    suspend fun getProjects() = apiService.getProjects()
    suspend fun getProjectDetail(id: String) = apiService.getProjectDetail(id)
    suspend fun getBands() = apiService.getBands()
    suspend fun getMusicians() = apiService.getMusicians()
    suspend fun getBandsDetail(id: String) = apiService.getBandsDetail(id)
    suspend fun getMusiciansDetail(id: String) = apiService.getMusiciansDetail(id)
    suspend fun getCollectors() = apiService.getCollectors()
    suspend fun getCollectorsDetail(id: String) = apiService.getCollectorsDetail(id)
    suspend fun postAlbumTrack(id: String, track: JsonObject) = apiService.postAlbumTrack(id, track)
    suspend fun postAlbum(album: JsonObject) = apiService.postAlbum(album)
}