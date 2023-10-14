package com.example.vinilos.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.ui.main.viewmodel.CollectorViewModel
import com.example.vinilos.data.repository.CollectorRepository

class CollectorViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
            return CollectorViewModel(CollectorRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Clase desconocida")
    }

}