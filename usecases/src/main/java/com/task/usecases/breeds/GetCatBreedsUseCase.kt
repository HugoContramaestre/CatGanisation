package com.task.usecases.breeds

import com.task.data.repository.MainRepository

class GetCatBreedsUseCase(private val repository: MainRepository) {
    suspend fun invoke() = repository.getBreeds()
}