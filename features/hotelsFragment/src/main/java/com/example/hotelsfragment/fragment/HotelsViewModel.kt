package com.example.hotelsfragment.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.di.AppDispatchers
import com.example.domain.di.Dispatcher
import com.example.domain.repository.HotelsRepository
import com.example.hotelsfragment.model.asUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelsViewModel @Inject constructor(
    private val hotelsRepository: HotelsRepository,
    @Dispatcher(AppDispatchers.IO) private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val _uiState: MutableStateFlow<HotelsUiState> =
        MutableStateFlow(HotelsUiState.FirstLoad)
    val uiState: StateFlow<HotelsUiState> = _uiState.asStateFlow()

    fun fetchHotels() {
        viewModelScope.launch(dispatcherIO) {
            _uiState.value = HotelsUiState.FirstLoad
            hotelsRepository.fetchAllHotels()
                .onSuccess {
                    val hotels = it.map { hotel -> hotel.asUi() }
                    _uiState.value = HotelsUiState.Content(hotels)
                }
                .onFailure {
                    _uiState.value = HotelsUiState.Error(it.message.toString())
                }
        }
    }
}