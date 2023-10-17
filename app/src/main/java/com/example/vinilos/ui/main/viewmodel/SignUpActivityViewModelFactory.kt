package com.example.vinilos.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.repository.AuthRepository
import java.security.InvalidParameterException

class SignUpActivityViewModelFactory (
    private val authRepository: AuthRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpActivityViewModel::class.java)) {
            return SignUpActivityViewModel(authRepository, application) as T
        }
        throw InvalidParameterException("Unable to construct RegisterActivityViewModel")
    }
}