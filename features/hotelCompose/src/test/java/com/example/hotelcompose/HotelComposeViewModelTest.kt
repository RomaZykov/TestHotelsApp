package com.example.hotelcompose

import com.example.domain.model.Hotel
import com.example.domain.repository.HotelsRepository
import com.example.hotelcompose.model.ErrorUiState
import com.example.hotelcompose.model.HotelUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HotelComposeViewModelTest {

    private val hotels = listOf(
        Hotel(
            id = 1,
            name = "Hotel 1",
            address = "Address №1",
            stars = 3.7,
            distance = 157.3,
            suitesAvailability = listOf(1, 2, 8, 7),
            imageUrl = "https//example-1-url.com/"
        ),
        Hotel(
            id = 2,
            name = "Hotel 2",
            address = "Address №2",
            stars = 0.0,
            distance = 999.0,
            suitesAvailability = listOf(),
            imageUrl = "https//example-2-url.com/451.jpg"
        ),
        Hotel(
            id = 3,
            name = "Hotel 3",
            address = "Address №3",
            stars = 5.0,
            distance = 0.1,
            suitesAvailability = listOf(1),
            imageUrl = "https//example-3-url.com/null"
        )
    )

    @Test
    fun `loadHotelById works successfully and updates state flow`() = runTest {
        val fakeHotelsRepository = FakeHotelsRepository(showError = false)
        val viewModel = HotelComposeViewModel.Base(
            fakeHotelsRepository,
            UnconfinedTestDispatcher()
        )

        viewModel.loadHotelById(2)

        val actualUiState = viewModel.uiState().value
        val expectedUiState = HotelUiState(
            name = hotels[1].name,
            stars = hotels[1].stars,
            address = hotels[1].address,
            distance = hotels[1].distance,
            suitesAvailability = hotels[1].suitesAvailability,
            imageId = "451.jpg",
            imageUrl = hotels[1].imageUrl
        )
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `loadHotelById throw correct Exception and updates to Error state flow`() = runTest {
        val fakeHotelsRepository = FakeHotelsRepository(showError = true)
        val viewModel = HotelComposeViewModel.Base(
            fakeHotelsRepository,
            UnconfinedTestDispatcher()
        )

        viewModel.loadHotelById(2)

        val actualUiState = viewModel.uiState().value
        val expectedUiState = ErrorUiState("Service unavailable")
        assertEquals(expectedUiState, actualUiState)
    }

    inner class FakeHotelsRepository(private val showError: Boolean) : HotelsRepository {

        override suspend fun fetchAllHotels(): Result<List<Hotel>> {
            return try {
                if (showError) {
                    throw IOException()
                }
                Result.success(hotels)
            } catch (e: Exception) {
                Result.failure(Exception("Service unavailable"))
            }
        }

        override suspend fun getHotel(id: Int): Result<Hotel> {
            return try {
                if (showError) {
                    throw IOException()
                }
                Result.success(hotels.find { it.id == id }!!)
            } catch (e: Exception) {
                Result.failure(Exception("Service unavailable"))
            }
        }
    }
}