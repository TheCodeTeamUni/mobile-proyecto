package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiService
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.CollectorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CollectorService {
    private val retrofit = RetrofitBuilder.getRetrofit()

    suspend fun getCollectors():List<CollectorResponse> {
        return   withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiService::class.java).getAllCollectors()
            response.body() ?: emptyList()
        }
    }


}