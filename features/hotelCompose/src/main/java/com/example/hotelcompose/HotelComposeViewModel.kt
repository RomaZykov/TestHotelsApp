package com.example.hotelcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.di.AppDispatchers
import com.example.domain.di.Dispatcher
import com.example.domain.repository.HotelsRepository
import com.example.hotelcompose.model.ErrorUiState
import com.example.hotelcompose.model.HotelUiState
import com.example.hotelcompose.model.LoadingHotelUiState
import com.example.hotelcompose.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HotelComposeViewModel {

    fun uiState(): StateFlow<UiState>

    fun loadHotelById(hotelId: Int) = Unit

    @HiltViewModel
    class Base @Inject constructor(
        private val hotelsRepository: HotelsRepository,
        @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
    ) : ViewModel(), HotelComposeViewModel {

        private val _uiState = MutableStateFlow<UiState>(LoadingHotelUiState)
        override fun uiState(): StateFlow<UiState> = _uiState

        override fun loadHotelById(hotelId: Int) {
            viewModelScope.launch(dispatcher) {
                hotelsRepository.getHotel(hotelId)
                    .onSuccess {
                        _uiState.value = HotelUiState(
                            it.name,
                            it.stars,
                            it.address,
                            it.distance,
                            it.suitesAvailability,
                            it.imageUrl.substringAfterLast('/'),
                            it.imageUrl
                        )
                    }
                    .onFailure {
                        _uiState.value = ErrorUiState(it.message.toString())
                    }
            }
        }
    }
}