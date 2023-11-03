package com.example.vinilos.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.repository.RegisterInformationRepository
import java.security.InvalidParameterException

class RegisterEducationViewModelFactory(
    private val registerInformation: RegisterInformationRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterEducationViewModel::class.java)) {
            return RegisterEducationViewModel(registerInformation, application) as T
        }
        throw InvalidParameterException("Unable to construct RegisterEducationViewModel")
    }
}