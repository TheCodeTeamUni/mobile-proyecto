package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiService
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.ArtistResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArtistService {
    private val retrofit = RetrofitBuilder.getRetrofit()

    suspend fun getArtist(): List<ArtistResponse> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiService::class.java).getAllArtist()
            response.body() ?: emptyList()
        }
    }


}