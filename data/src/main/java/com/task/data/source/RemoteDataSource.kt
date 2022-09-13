package com.task.data.source

import com.task.data.exception.Failure
import com.task.data.functional.Either
import com.task.domain.CatBreed
import com.task.domain.LoginResponse

interface RemoteDataSource {
    suspend fun login(username: String, password: String): Either<Failure, LoginResponse>
    suspend fun getBreedsList(): Either<Failure, List<CatBreed>>
    suspend fun getBreedsByCountry(country: String): Either<Failure, List<CatBreed>>
}