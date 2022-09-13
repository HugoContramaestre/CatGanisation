package com.task.catganisation.data.api.response

import com.google.gson.annotations.SerializedName
import com.task.domain.LoginResponse as LoginResponseDomain

data class LoginResponse(
    @SerializedName("token") val token: String? = null,
    @SerializedName("tokenExpire") val tokenExpire: String? = null,
    @SerializedName("tokenRefresh") val tokenRefresh: String? = null
) {
    fun toDomain() = LoginResponseDomain(
        token = token ?: "",
        tokenExpire = tokenExpire ?: "",
        tokenRefresh = tokenRefresh ?: ""
    )
}