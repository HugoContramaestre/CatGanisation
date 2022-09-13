package com.task.data.repository

import com.task.data.source.RemoteDataSource

class MainRepository(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun login(username: String, password: String) = remoteDataSource.login(username, password)

    suspend fun getBreeds() = remoteDataSource.getBreedsList()

    suspend fun getBreedsByCountry(country: String) = remoteDataSource.getBreedsByCountry(country)
}