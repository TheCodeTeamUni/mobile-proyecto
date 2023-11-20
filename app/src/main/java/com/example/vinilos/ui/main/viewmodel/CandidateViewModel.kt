package com.example.vinilos.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.vinilos.data.repository.CandidateRepository
import com.example.vinilos.utils.Resource
import kotlinx.coroutines.Dispatchers

class CandidateViewModel(private val candidateRepository: CandidateRepository) : ViewModel() {
    fun getCandidates() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = candidateRepository.getCandidates()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Un error ha ocurrido!"))
        }
    }

    fun getCandidateDetail(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = candidateRepository.getCandidateDetail(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Un error ha ocurrido!"))
        }
    }
}