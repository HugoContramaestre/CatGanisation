package com.task.catganisation.parcel

import android.os.Parcelable
import com.task.domain.Image as ImageDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    var id: String,
    var width: Int,
    var height: Int,
    var url: String
) : Parcelable {
    fun toDomain() = ImageDomain(
        id,
        width,
        height,
        url
    )
}

fun ImageDomain.toParcel() = Image(
    id,
    width,
    height,
    url
)

