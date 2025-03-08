package com.example.data

import com.example.exceptions.HandleError
import com.example.domain.model.Hotel
import com.example.domain.repository.HotelsRepository
import com.example.network.HotelNetworkDataSource
import javax.inject.Inject

class HotelsRepositoryImpl @Inject constructor(
    private val hotelNetworkDataSource: HotelNetworkDataSource,
    private val handleError: HandleError
) : HotelsRepository {
    override suspend fun fetchAllHotels(): Result<List<Hotel>> {
        return try {
            val hotels = hotelNetworkDataSource.allHotels().map {
                it.asData().mappedValue()
            }
            Result.success(hotels)
        } catch (e: Exception) {
            Result.failure(handleError.handle(e))
        }
    }

    override suspend fun getHotel(id: Int): Result<Hotel> {
        return try {
            val hotel = hotelNetworkDataSource.getHotel(id).asData()
            Result.success(hotel.mappedValue())
        } catch (e: Exception) {
            Result.failure(handleError.handle(e))
        }
    }
}