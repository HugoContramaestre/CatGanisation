package com.task.usecases.login

import com.task.data.repository.MainRepository

class LoginUseCase(private val repository: MainRepository) {
    suspend fun invoke(username: String, password: String) = repository.login(username, password)
}