package com.example.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HotelNetwork(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("stars") val stars: Double? = null,
    @SerializedName("distance") val distance: Double? = null,
    @SerializedName("suites_availability") val suitesAvailability: String? = null,
    @SerializedName("image") val imageId: String? = null,
    val imageUrl: String? = null
) : Parcelable