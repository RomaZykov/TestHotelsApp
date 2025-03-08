package com.example.data

import com.example.domain.Mapper
import com.example.domain.model.Hotel
import com.example.network.HotelNetwork

data class HotelData(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Double,
    val distance: Double,
    val suitesAvailability: List<Int>,
    val imageUrl: String
) : Mapper<Hotel> {
    override fun mappedValue(): Hotel {
        return Hotel(
            id = this.id,
            name = this.name,
            address = this.address,
            stars = this.stars,
            distance = this.distance,
            suitesAvailability = this.suitesAvailability,
            imageUrl = this.imageUrl
        )
    }
}

fun HotelNetwork.asData() = HotelData(
    id = this.id ?: 0,
    name = this.name ?: "",
    address = this.address ?: "",
    stars = this.stars ?: 0.0,
    distance = this.distance ?: 0.0,
    suitesAvailability = this.suitesAvailability?.split(":")?.map { it.toIntOrNull() ?: 0 }
        ?.filter { it != 0 } ?: emptyList(),
    imageUrl = imageUrl ?: ""
)
