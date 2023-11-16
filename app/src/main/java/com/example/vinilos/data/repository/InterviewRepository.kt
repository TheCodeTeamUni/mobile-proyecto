package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiHelper
import com.google.gson.JsonObject

class InterviewRepository(private val apiHelper: ApiHelper) {

    suspend fun getInterviews() = apiHelper.getInterviews()

    suspend fun getInterviewDetail(id: String) = apiHelper.getInterviewDetail(id)

    suspend fun postAlbumTrack(id: String, track: JsonObject) = apiHelper.postAlbumTrack(id, track)

    suspend fun postAlbum(album: JsonObject) = apiHelper.postAlbum(album)
}