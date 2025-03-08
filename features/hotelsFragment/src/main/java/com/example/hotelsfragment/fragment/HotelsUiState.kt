package com.example.hotelsfragment.fragment

import com.example.hotelsfragment.model.HotelUi

sealed interface HotelsUiState {

    data object FirstLoad : HotelsUiState

    data class Content(val hotels: List<HotelUi>) : HotelsUiState

    data class Error(val message: String) : HotelsUiState
}