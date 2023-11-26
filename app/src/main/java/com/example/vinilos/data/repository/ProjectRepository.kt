package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiHelper

class ProjectRepository(private val apiHelper: ApiHelper) {

    suspend fun getProjects() = apiHelper.getProjects()
}