package com.example.vinilos.data.repository

import com.example.vinilos.data.api.ApiHelper

class CollectorRepository(private val apiHelper: ApiHelper) {

    suspend fun getCollectors() = apiHelper.getCollectors()
    suspend fun getCollectorsDetail(id: String) = apiHelper.getCollectorsDetail(id)
}