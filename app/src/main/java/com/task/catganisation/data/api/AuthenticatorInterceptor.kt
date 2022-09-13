package com.task.catganisation.data.api

import okhttp3.Interceptor
import okhttp3.Response

private const val HEADER_AUTHORIZATION = "x-api-key"
private const val HEADER_CONTENT_TYPE = "Content-Type"

class AuthenticatorInterceptor(
    private val apiKey: String
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(HEADER_AUTHORIZATION, apiKey)
            .addHeader(HEADER_CONTENT_TYPE, "application/json")
            .build()
        return chain.proceed(request)
    }

}