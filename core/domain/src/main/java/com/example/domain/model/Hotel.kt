package com.example.domain.model

data class Hotel(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Double,
    val distance: Double,
    val suitesAvailability: List<Int>,
    val imageUrl: String
)
