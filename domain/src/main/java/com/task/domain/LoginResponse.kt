package com.task.domain

data class LoginResponse(
    val token: String,
    val tokenExpire: String,
    val tokenRefresh: String
)
