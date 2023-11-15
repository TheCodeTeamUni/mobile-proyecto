package com.example.vinilos.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.data.repository.ProjectRepository
import com.example.vinilos.ui.main.viewmodel.ProjectViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)) {
            return ProjectViewModel(ProjectRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Clase desconocida")
    }

}