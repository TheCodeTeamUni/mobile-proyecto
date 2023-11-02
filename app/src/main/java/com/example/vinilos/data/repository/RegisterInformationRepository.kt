package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiService
import com.example.vinilos.data.model.RegisterCandidatePersonalInformationBody
import com.example.vinilos.data.model.RegisterCandidateWorkExperienceInformationBody
import com.example.vinilos.utils.RequestStatus
import com.example.vinilos.utils.SimplifiedMessage
import kotlinx.coroutines.flow.flow

class RegisterInformationRepository(private val consumer: ApiService) {
    fun personalInformationRegister(body: RegisterCandidatePersonalInformationBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.registerCandidatePersonalInformation(body)
        if (response.isSuccessful) {
            emit(RequestStatus.Success(response.body()!!))
        } else {
            emit(
                RequestStatus.Error(
                    SimplifiedMessage.get(
                        response.errorBody()!!.byteStream().reader().readText()
                    )
                )
            )
        }
    }

    fun registerWorkExperience(body: RegisterCandidateWorkExperienceInformationBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.registerCandidateWorkExperienceInformation(body)
        if (response.isSuccessful) {
            emit(RequestStatus.Success(response.body()!!))
        } else {
            emit(
                RequestStatus.Error(
                    SimplifiedMessage.get(
                        response.errorBody()!!.byteStream().reader().readText()
                    )
                )
            )
        }
    }
}