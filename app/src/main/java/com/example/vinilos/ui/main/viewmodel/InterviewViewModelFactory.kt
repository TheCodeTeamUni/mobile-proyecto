package com.example.vinilos.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.data.repository.InterviewRepository

class InterviewViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InterviewViewModel::class.java)) {
            return InterviewViewModel(InterviewRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Clase desconocida")
    }

}