package com.example.vinilos.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.vinilos.data.model.ProjectModel
import com.example.vinilos.data.model.TracksModel
import com.example.vinilos.data.repository.ProjectRepository
import com.example.vinilos.utils.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectViewModel(private val AlbumRepository: ProjectRepository) : ViewModel() {
    fun getProjects() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = AlbumRepository.getProjects()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Un error ha ocurrido!"))
        }
    }

    fun getProjectDetail(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = AlbumRepository.getProjectDetail(id)))
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

    suspend fun createAlbumPost(album: ProjectModel) = withContext(Dispatchers.IO) {
        AlbumRepository.postAlbum(jsonPostAlbumString(album))
    }

    private fun jsonPostString(name: String, duration: String): JsonObject {
        val paramObject = JsonObject()
        paramObject.addProperty("name", name)
        paramObject.addProperty("duration", duration)
        return paramObject
    }

    private fun jsonPostAlbumString(album: ProjectModel): JsonObject {
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