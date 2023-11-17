package com.example.vinilos.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.vinilos.data.model.CreateProjectBody
import com.example.vinilos.data.model.ProjectModel
import com.example.vinilos.data.model.TracksModel
import com.example.vinilos.data.repository.ProjectRepository
import com.example.vinilos.utils.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectViewModel(private val projectRepository: ProjectRepository) : ViewModel() {
    fun getProjects() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = projectRepository.getProjects()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Un error ha ocurrido!"))
        }
    }

    fun getProjectDetail(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = projectRepository.getProjectDetail(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Un error ha ocurrido!"))
        }
    }

    suspend fun createTrackToAlbum(name: String, duration: String, id: Number) =
        withContext(Dispatchers.IO) {
            val track = TracksModel(name, duration)
            projectRepository.postAlbumTrack(
                id.toString(),
                jsonPostString(track.name, track.duration)
            )
        }

    suspend fun createAlbumPost(project: CreateProjectBody) = withContext(Dispatchers.IO) {
        projectRepository.postAlbum(jsonPostAlbumString(project))
    }

    private fun jsonPostString(name: String, duration: String): JsonObject {
        val paramObject = JsonObject()
        paramObject.addProperty("name", name)
        paramObject.addProperty("duration", duration)
        return paramObject
    }

    private fun jsonPostAlbumString(project: CreateProjectBody): JsonObject {
        val paramObject = JsonObject()
        paramObject.addProperty("nameProject", project.nameProject)
        paramObject.addProperty("startDate", project.startDate)
        paramObject.addProperty("endDate", project.endDate)
        paramObject.addProperty("description", project.description)
        paramObject.addProperty("aspirants", project.aspirants)
        return paramObject
    }
}