package com.task.catganisation.parcel

import android.os.Parcelable
import com.task.domain.CatBreed as CatBreedDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatBreed(
    var id: String,
    var name: String,
    var origin: String,
    var countryCode: String,
    var countryCodes: String,
    var description: String,
    var wikipediaUrl: String,
    var image: Image,
    var temperament: String
): Parcelable {
    fun toDomain() = CatBreedDomain(
        id = id,
        name = name,
        origin = origin,
        countryCode = countryCode,
        countryCodes = countryCodes,
        description = description,
        wikipediaUrl = wikipediaUrl,
        image = image.toDomain(),
        temperament = temperament
    )
}

fun CatBreedDomain.toParcel() = CatBreed(
    id = id,
    name = name,
    origin = origin,
    countryCode = countryCode,
    countryCodes = countryCodes,
    description = description,
    wikipediaUrl = wikipediaUrl,
    image = image.toParcel(),
    temperament = temperament
)