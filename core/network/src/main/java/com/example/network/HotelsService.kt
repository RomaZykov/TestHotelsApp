package com.example.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HotelsService {
    @GET("iMofas/ios-android-test/master/0777.json")
    fun getHotels(): Call<List<HotelNetwork>>

    @GET("iMofas/ios-android-test/master/{id}.json")
    fun getHotel(@Path("id") hotelId: Int): Call<HotelNetwork>
}
