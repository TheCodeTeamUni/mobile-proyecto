package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiService
import com.example.vinilos.data.model.LoginBody
import com.example.vinilos.data.model.RegisterBody
import com.example.vinilos.data.model.ValidateEmailBody
import com.example.vinilos.utils.RequestStatus
import com.example.vinilos.utils.SimplifiedMessage
import kotlinx.coroutines.flow.flow

class AuthRepository(private val consumer: ApiService) {
    fun validateEmailAddress(body: ValidateEmailBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.validateEmailAddress(body)
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

    fun registerUser(body: RegisterBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.registerUser(body)
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

    fun loginUser(body: LoginBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.loginUser(body)
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