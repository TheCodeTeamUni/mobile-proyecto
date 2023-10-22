package com.example.vinilos.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.LoginBody
import com.example.vinilos.data.RegisterBody
import com.example.vinilos.data.User
import com.example.vinilos.data.ValidateEmailBody
import com.example.vinilos.data.repository.AuthRepository
import com.example.vinilos.utils.RequestStatus
import kotlinx.coroutines.launch

class MainActivityViewModel(val authRepository: AuthRepository, val application: Application) :
    ViewModel() {
    private var isLoading: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    private var user: MutableLiveData<User> = MutableLiveData()

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
    fun getUser(): LiveData<User> = user

    fun loginUser(body: LoginBody) {
        viewModelScope.launch {
            authRepository.loginUser(body).collect {
                when (it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                        println("Esperando respuesta")
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        user.value = it.data.user
                        println("Respuesta obtenida")
                    }
                    is RequestStatus.Error -> {
                        isLoading.value = false
                        errorMessage.value = it.message
                        println("Se presento un error")
                    }
                }
            }
        }
    }
}