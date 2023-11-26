package com.example.vinilos.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getProjects() = apiService.getProjects()
    suspend fun getInterviews() = apiService.getInterviews()
    suspend fun getInterviewDetail(id: String) = apiService.getInterviewDetail(id)
    suspend fun getCandidates() = apiService.getCandidates()
    suspend fun getCandidateDetail(id: String) = apiService.getCandidateDetail(id)

}