package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiHelper

class CandidateRepository (private val apiHelper: ApiHelper){

    suspend fun getCandidates() = apiHelper.getCandidates()
    suspend fun getCandidateDetail(id: String) = apiHelper.getCandidateDetail(id)
}