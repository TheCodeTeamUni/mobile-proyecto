package com.example.vinilos.data.api

import com.google.gson.JsonObject

class ApiHelper(private val apiService: ApiService) {

    suspend fun getProjects() = apiService.getProjects()
    suspend fun getProjectDetail(id: String) = apiService.getProjectDetail(id)
    suspend fun getInterviews() = apiService.getInterviews()
    suspend fun getInterviewDetail(id: String) = apiService.getInterviewDetail(id)

}