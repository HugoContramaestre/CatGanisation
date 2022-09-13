package com.task.catganisation.data.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.task.catganisation.data.api.response.*
import retrofit2.http.*

interface ApiServices {

    @GET("breeds")
    suspend fun getBreeds(): NetworkResponse<List<CatBreed>, ErrorResult>

    @GET("breeds/search")
    suspend fun getBreedsByCountryText(@Query("q") text: String):
            NetworkResponse<List<CatBreed>, ErrorResult>

}