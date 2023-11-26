package com.example.vinilos.utils

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader(
            "Authorization",
            "Bearer " + AuthToken.getInstance(MyApplication.instance.applicationContext).token,
        )
            .build()
        return chain.proceed(request)
    }
}