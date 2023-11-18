package com.example.vinilos.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.vinilos.data.model.ProjectModel
import com.example.vinilos.data.repository.InterviewRepository
import com.example.vinilos.utils.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InterviewViewModel(private val InterviewRepository: InterviewRepository) : ViewModel() {
    fun getInterviews() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = InterviewRepository.getInterviews()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Un error ha ocurrido!"))
        }
    }

    fun getInterviewDetail(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = InterviewRepository.getInterviewDetail(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Un error ha ocurrido!"))
        }
    }
}