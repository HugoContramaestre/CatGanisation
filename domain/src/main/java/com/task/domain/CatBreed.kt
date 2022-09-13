package com.task.domain

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
): ContentContract() {
    override fun id(): String {
        return id
    }
}
