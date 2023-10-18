package com.example.vinilos.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL =
        "http://jobs-app-users-env.eba-pfppdukn.us-east-1.elasticbeanstalk.com/"

    val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    fun getService(): ApiService {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit: Retrofit = builder.build()
        return retrofit.create(ApiService::class.java)
    }
}