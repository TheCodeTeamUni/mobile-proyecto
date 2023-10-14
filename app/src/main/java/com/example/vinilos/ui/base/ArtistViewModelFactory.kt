package com.example.vinilos.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.ui.main.viewmodel.ArtistViewModel
import com.example.vinilos.data.repository.ArtistRepository

class ArtistViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtistViewModel::class.java)) {
            return ArtistViewModel(ArtistRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Clase desconocida")
    }
}