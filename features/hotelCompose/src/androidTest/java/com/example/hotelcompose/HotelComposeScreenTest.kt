package com.example.hotelcompose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hotelcompose.model.HotelUiState
import com.example.hotelcompose.model.LoadingHotelUiState
import com.example.hotelcompose.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HotelComposeScreenTest : BaseComposeTest() {

    @Test
    fun loadingScreen_whenFirstInvoke_displayCorrectly() {
        var wasLoadHotelWithIdCalled = false
        var loadingUiStateFlowCalled = false
        var passedHotelId: Int? = null
        composeTestRule.setContent {
            HotelComposeScreen(
                onNavigateBack = {},
                hotelId = 2,
                viewModel = object : HotelComposeViewModel {
                    override fun uiState(): StateFlow<UiState> {
                        loadingUiStateFlowCalled = true
                        return MutableStateFlow(LoadingHotelUiState)
                    }

                    override fun loadHotelById(hotelId: Int) {
                        passedHotelId = hotelId
                        wasLoadHotelWithIdCalled = true
                    }
                }
            )
        }

        composeTestRule
            .onNodeWithContentDescription("CircularProgressIndicator")
            .assertExists()
            .assertIsDisplayed()

        assertEquals(2, passedHotelId)
        assertTrue(wasLoadHotelWithIdCalled)
        assertTrue(loadingUiStateFlowCalled)
    }

    @Test
    fun hotelScreen_afterLoading_displayCorrectly() {
        var hotelUiStateFlowCalled = false
        var wasLoadHotelWithIdCalled = false
        var passedHotelId: Int? = null
        val suitesAvailable = listOf(63, 47)

        composeTestRule.setContent {
            HotelComposeScreen(
                onNavigateBack = {},
                hotelId = 2,
                viewModel = object : HotelComposeViewModel {
                    override fun uiState(): StateFlow<UiState> {
                        hotelUiStateFlowCalled = true
                        return MutableStateFlow(
                            HotelUiState(
                                name = "Test name",
                                stars = 3.8,
                                address = "Test address",
                                distance = 154.4,
                                suitesAvailability = suitesAvailable,
                                imageId = "57.jpg",
                                imageUrl = "https//test.com"
                            )
                        )
                    }

                    override fun loadHotelById(hotelId: Int) {
                        passedHotelId = hotelId
                        wasLoadHotelWithIdCalled = true
                    }
                }
            )
        }

        hotelUiScreen(suitesAvailable.size, true)

        assertEquals(2, passedHotelId)
        assertTrue(wasLoadHotelWithIdCalled)
        assertTrue(hotelUiStateFlowCalled)
    }

    @Test
    fun hotelScreen_afterLoadingWithEmptyImageAndSuites_displayCorrectly() {
        var hotelUiStateFlowCalled = false
        var wasLoadHotelWithIdCalled = false
        var passedHotelId: Int? = null
        composeTestRule.setContent {
            HotelComposeScreen(
                onNavigateBack = {},
                hotelId = 2,
                viewModel = object : HotelComposeViewModel {
                    override fun uiState(): StateFlow<UiState> {
                        hotelUiStateFlowCalled = true
                        return MutableStateFlow(
                            HotelUiState(
                                name = "Test name",
                                stars = 3.8,
                                address = "Test address",
                                distance = 154.4,
                                suitesAvailability = emptyList(),
                                imageId = "null",
                                imageUrl = "https//test.com"
                            )
                        )
                    }

                    override fun loadHotelById(hotelId: Int) {
                        passedHotelId = hotelId
                        wasLoadHotelWithIdCalled = true
                    }
                }
            )
        }

        hotelUiScreen(0, hasImage = false)

        assertEquals(2, passedHotelId)
        assertTrue(wasLoadHotelWithIdCalled)
        assertTrue(hotelUiStateFlowCalled)
    }

    private fun hotelUiScreen(suites: Int, hasImage: Boolean) {
        if (hasImage) {
            composeTestRule
                .onNodeWithContentDescription("hotel image")
                .assertExists()
                .assertIsDisplayed()

            composeTestRule
                .onNodeWithContentDescription("placeholder hotel image")
                .assertDoesNotExist()
        } else {
            composeTestRule
                .onNodeWithContentDescription("placeholder hotel image")
                .assertExists()
                .assertIsDisplayed()

            composeTestRule
                .onNodeWithContentDescription("hotel image")
                .assertDoesNotExist()
        }

        composeTestRule
            .onNodeWithText("Test name")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("3.8")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Hotel address")
            .assertTextEquals("Test address")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Hotel distance")
            .assertTextEquals("154.4 м")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Hotel suites available")
            .assertTextEquals(if (suites == 0) "Нет свободных комнат" else "Свободные комнаты №: 63, 47")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Назад")
            .assertExists()
            .assertIsDisplayed()
    }
}