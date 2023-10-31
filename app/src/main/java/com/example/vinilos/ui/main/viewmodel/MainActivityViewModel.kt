package com.example.vinilos.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.model.LoginBody
import com.example.vinilos.data.model.User
import com.example.vinilos.data.repository.AuthRepository
import com.example.vinilos.utils.AuthToken
import com.example.vinilos.utils.RequestStatus
import kotlinx.coroutines.launch

class MainActivityViewModel(val authRepository: AuthRepository, val application: Application) : ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    private var user: MutableLiveData<User> = MutableLiveData()
    private var login: MutableLiveData<String> = MutableLiveData()

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
    fun getLogin(): LiveData<String> = login
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
                        login.value = it.data.type
                        AuthToken.getInstance(application.baseContext).token = it.data.token
                        //login.value = it.data.token
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