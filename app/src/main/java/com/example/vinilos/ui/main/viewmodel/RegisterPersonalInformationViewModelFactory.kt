package com.example.vinilos.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.repository.RegisterInformationRepository
import java.security.InvalidParameterException

class RegisterPersonalInformationViewModelFactory(
    private val registerInformationRepository: RegisterInformationRepository,
    private val application: Application
    ) : ViewModelProvider.Factory  {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterPersonalInformationViewModel::class.java)) {
            return RegisterPersonalInformationViewModel(registerInformationRepository, application) as T
        }
        throw InvalidParameterException("Unable to construct RegisterPersonalInformationViewModel")
    }
}