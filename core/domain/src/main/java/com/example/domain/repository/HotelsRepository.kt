package com.example.domain.repository

import com.example.domain.model.Hotel

interface HotelsRepository {

    suspend fun fetchAllHotels() : Result<List<Hotel>>

    suspend fun getHotel(id: Int) : Result<Hotel>
}