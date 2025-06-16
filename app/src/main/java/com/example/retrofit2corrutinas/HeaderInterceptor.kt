package com.example.retrofit2corrutinas

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().
            addHeader(
                "Accept", "application/json")
            .addHeader(
                "ApiKey", "123456789")
            .build()
        return chain.proceed(request)
    }
}
