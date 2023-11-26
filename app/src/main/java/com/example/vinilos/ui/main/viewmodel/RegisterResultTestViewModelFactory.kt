package com.example.vinilos.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.repository.RegisterInformationRepository
import java.security.InvalidParameterException

class RegisterResultTestViewModelFactory (
    private val registerInformation: RegisterInformationRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterResultTestViewModel::class.java)) {
            return RegisterResultTestViewModel(registerInformation, application) as T
        }
        throw InvalidParameterException("Unable to construct RegisterResultTestViewModel")
    }
}