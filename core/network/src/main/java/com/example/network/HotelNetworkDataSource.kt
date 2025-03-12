package com.example.network

import com.example.exceptions.TestHotelsAppException
import javax.inject.Inject

interface HotelNetworkDataSource {

    suspend fun allHotels(): List<HotelNetwork>

    suspend fun getHotel(id: Int): HotelNetwork

    class Base @Inject constructor(
        private val hotelsService: HotelsService
    ) : HotelNetworkDataSource {

        override suspend fun allHotels(): List<HotelNetwork> {
            val hotels = hotelsService.getHotels().execute().body()
            return hotels ?: throw TestHotelsAppException.ServiceUnavailableException()
        }

        override suspend fun getHotel(id: Int): HotelNetwork {
            val hotel = hotelsService.getHotel(id).execute().body()
            val result = hotel?.let { hotel.copy(imageUrl = IMAGE_URL + hotel.imageId) }
            return result ?: throw TestHotelsAppException.ServiceUnavailableException()
        }
    }

    companion object {
        const val IMAGE_URL = "https://github.com/iMofas/ios-android-test/raw/master/"
    }
}