package com.task.catganisation.data.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.task.data.exception.Failure
import com.task.data.functional.Either
import com.task.data.source.RemoteDataSource
import com.task.domain.CatBreed
import com.task.domain.LoginResponse
import com.task.catganisation.data.api.response.LoginResponse as LoginResponseService

class ApiRemoteDataSource(private val api: ApiServices) : RemoteDataSource {
    override suspend fun login(username: String, password: String): Either<Failure, LoginResponse> {
        return Either.Right(LoginResponseService("asdasd", "1120087865633", "qsdfasdasdaa").toDomain())
    }

    override suspend fun getBreedsList(): Either<Failure, List<CatBreed>> {
        return api.getBreeds()
            .wrapperResponse { breed -> breed.map{ i-> i.toDomain() }
                .filter { it.id.isNotEmpty() } }
    }

    override suspend fun getBreedsByCountry(country: String): Either<Failure, List<CatBreed>> {
        return api.getBreedsByCountryText(country)
            .wrapperResponse { breed -> breed.map { i -> i.toDomain() }
                .filter { it.id.isNotEmpty() } }
    }

}

inline fun <In : Any, Out : Any> NetworkResponse<In, ErrorResult>.wrapperResponse(
    transform: ((In) -> Out)
): Either<Failure, Out> {
    return when (this) {
        is NetworkResponse.Success -> Either.Right(transform.invoke(body))
        is NetworkResponse.ServerError -> {
            error?.printStackTrace()
            Either.Left(
                FailureFactory().handleApiCode(
                    code,
                    body
                )
            )
        }
        is NetworkResponse.NetworkError -> {
            error.printStackTrace()
            Either.Left(Failure.NetworkConnection)
        }
        is NetworkResponse.UnknownError -> {
            error.printStackTrace()
            Either.Left(Failure.UnknownApiError)
        }
    }
}

fun <In : Any> NetworkResponse<In, ErrorResult>.wrapperResponseEmpty(): Failure? {
    return when (this) {
        is NetworkResponse.Success -> null
        is NetworkResponse.ServerError ->
            FailureFactory().handleApiCode(
                code,
                body
            )
        else -> Failure.NetworkConnection
    }
}