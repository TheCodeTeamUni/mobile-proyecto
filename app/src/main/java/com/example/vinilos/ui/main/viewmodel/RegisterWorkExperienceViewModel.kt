package com.example.vinilos.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.model.RegisterCandidateWorkExperienceInformationBody
import com.example.vinilos.data.repository.RegisterInformationRepository
import com.example.vinilos.utils.RequestStatus
import kotlinx.coroutines.launch

class RegisterWorkExperienceViewModel(
    private val registerInformation: RegisterInformationRepository, val application: Application
) : ViewModel() {
    private var isLoading: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    private var registerWorkExperience: MutableLiveData<Int> = MutableLiveData()

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
    fun getRegisterWorkExperience(): LiveData<Int> = registerWorkExperience

    fun registerWorkExperience(body: RegisterCandidateWorkExperienceInformationBody) {
        viewModelScope.launch {
            registerInformation.registerWorkExperience(body).collect {
                when (it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                        println("Esperando respuesta")
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        registerWorkExperience.value = it.data.id
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