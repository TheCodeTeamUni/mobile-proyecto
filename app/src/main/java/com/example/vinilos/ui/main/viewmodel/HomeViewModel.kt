package com.example.vinilos.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.vinilos.data.model.AlbumModel
import com.example.vinilos.data.model.TracksModel
import com.example.vinilos.data.repository.AlbumRepository
import com.example.vinilos.utils.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel(private val AlbumRepository: AlbumRepository) : ViewModel() {
    fun getAlbums() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = AlbumRepository.getAlbums()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Un error ha ocurrido!"))
        }
    }

    fun getAlbumDetail(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = AlbumRepository.getAlbumDetail(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Un error ha ocurrido!"))
        }
    }

    suspend fun createTrackToAlbum(name: String, duration: String, id: Number) =
        withContext(Dispatchers.IO) {
            val track = TracksModel(name, duration)
            AlbumRepository.postAlbumTrack(
                id.toString(),
                jsonPostString(track.name, track.duration)
            )
        }

    suspend fun createAlbumPost(album: AlbumModel) = withContext(Dispatchers.IO) {
        AlbumRepository.postAlbum(jsonPostAlbumString(album))
    }

    private fun jsonPostString(name: String, duration: String): JsonObject {
        val paramObject = JsonObject()
        paramObject.addProperty("name", name)
        paramObject.addProperty("duration", duration)
        return paramObject
    }

    private fun jsonPostAlbumString(album: AlbumModel): JsonObject {
        val paramObject = JsonObject()
        paramObject.addProperty("name", album.name)
        paramObject.addProperty("cover", album.cover)
        paramObject.addProperty("releaseDate", album.dateCreation)
        paramObject.addProperty("description", album.description)
        paramObject.addProperty("genre", album.genre)
        paramObject.addProperty("recordLabel", album.record)
        return paramObject
    }
}