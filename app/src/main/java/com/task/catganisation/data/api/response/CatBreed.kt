package com.task.catganisation.data.api.response

import com.google.gson.annotations.SerializedName
import com.task.domain.CatBreed as CatBreedDomain

data class CatBreed(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("origin") val origin: String? = null,
    @SerializedName("country_code") val countryCode: String? = null,
    @SerializedName("country_codes") val countryCodes: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("wikipedia_url") val wikipediaUrl: String? = null,
    @SerializedName("image") val image: Image? = null,
    @SerializedName("temperament") val temperament: String?
) {
    fun toDomain() = CatBreedDomain(
        id ?: "",
        name ?: "",
        origin ?: "",
        countryCode ?: "",
        countryCodes ?: "",
        description ?: "",
        wikipediaUrl ?: "",
        image?.toDomain() ?: Image().toDomain(),
        temperament ?: ""
    )
}