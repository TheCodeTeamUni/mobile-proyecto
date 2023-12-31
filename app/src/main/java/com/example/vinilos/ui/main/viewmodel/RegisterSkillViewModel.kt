package com.example.vinilos.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.model.RegisterCandidateSkillInformationBody
import com.example.vinilos.data.repository.RegisterInformationRepository
import com.example.vinilos.utils.RequestStatus
import kotlinx.coroutines.launch

class RegisterSkillViewModel(
    private val registerInformation: RegisterInformationRepository, val application: Application
) : ViewModel() {
    private var isLoading: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    private var registerSkillInformation: MutableLiveData<Int> = MutableLiveData()

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
    fun getRegisterSkillInformation(): LiveData<Int> = registerSkillInformation

    fun registerSkillInformation(body: RegisterCandidateSkillInformationBody) {
        viewModelScope.launch {
            registerInformation.registerSkillInformation(body).collect {
                when (it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                        println("Esperando respuesta")
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        registerSkillInformation.value = it.data.id
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