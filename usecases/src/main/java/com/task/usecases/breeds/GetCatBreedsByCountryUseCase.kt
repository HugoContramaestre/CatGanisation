package com.task.usecases.breeds

import com.task.data.repository.MainRepository

class GetCatBreedsByCountryUseCase(private val repository: MainRepository) {
    suspend fun invoke(country: String) = repository.getBreedsByCountry(country)
}