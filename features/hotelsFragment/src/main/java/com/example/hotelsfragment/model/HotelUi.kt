package com.example.hotelsfragment.model

import com.example.domain.model.Hotel

data class HotelUi(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Double,
    val distance: Double,
    val suitesAvailability: List<Int>,
    val imageUrl: String
)

fun Hotel.asUi() = HotelUi(
    id = this.id,
    name = this.name,
    address = this.address,
    stars = this.stars,
    distance = this.distance,
    suitesAvailability = this.suitesAvailability,
    imageUrl = this.imageUrl
)
