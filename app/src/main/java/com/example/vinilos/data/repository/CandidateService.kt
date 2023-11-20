package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiService
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.CandidatesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CandidateService {
    private val retrofit = RetrofitBuilder.getRetrofit()
    suspend fun getCandidates(): List<CandidatesResponse> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiService::class.java).getAllCandidates()
            response.body() ?: emptyList()
        }
    }
}