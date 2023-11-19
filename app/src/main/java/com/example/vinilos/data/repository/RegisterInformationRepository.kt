package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiService
import com.example.vinilos.data.model.*
import com.example.vinilos.utils.RequestStatus
import com.example.vinilos.utils.SimplifiedMessage
import kotlinx.coroutines.flow.flow
import retrofit2.http.Path

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

    fun registerEducationInformation(body: RegisterCandidateEducationInformationBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.registerCandidateEducationInformation(body)
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

    fun registerSkillInformation(body: RegisterCandidateSkillInformationBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.registerCandidateSkillInformation(body)
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

    fun createProject(body: CreateProjectBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.createProject(body)
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

    fun createInterview(body: CreateInterviewBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.createInterview(body)
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

    fun assignedCandidate(body: AssignedCandidateBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.postAssignedCandidate("1", body)
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