package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiHelper

class InterviewRepository(private val apiHelper: ApiHelper) {

    suspend fun getInterviews() = apiHelper.getInterviews()
    suspend fun getInterviewDetail(id: String) = apiHelper.getInterviewDetail(id)

}