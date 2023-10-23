package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiService
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.AlbumResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AlbumService {
    private val retrofit = RetrofitBuilder.getRetrofit()

    suspend fun getAlbums(): List<AlbumResponse> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiService::class.java).getAllAlbums()
            response.body() ?: emptyList()
        }
    }

    suspend fun createAlbum(album: HashMap<String, String>): Response<AlbumResponse> {
        return retrofit.create(ApiService::class.java).createAlbum(album)
    }
}