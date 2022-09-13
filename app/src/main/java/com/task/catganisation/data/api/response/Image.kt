package com.task.catganisation.data.api.response

import com.google.gson.annotations.SerializedName
import com.task.domain.Image as ImageDomain

data class Image(
    @SerializedName("id") val id: String? = null,
    @SerializedName("width") val width: Int? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("url") val url: String? = null
){
    fun toDomain() = ImageDomain(
        id ?: "",
        width ?: -1,
        height ?: -1,
        url ?: ""
    )
}
