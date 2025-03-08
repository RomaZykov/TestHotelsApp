package com.example.hotelcompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hotelcompose.model.ErrorUiState
import com.example.hotelcompose.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HotelComposeScreen(
    onNavigateBack: () -> Unit,
    hotelId: Int?,
    viewModel: HotelComposeViewModel = hiltViewModel<HotelComposeViewModel.Base>()
) {
    val uiState by viewModel.uiState().collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadHotelById(hotelId!!)
    }

    uiState.Display(onNavigateBack)
}

@Preview(showSystemUi = true)
@Composable
fun HotelComposeScreenPreview() {
    val fakeViewModel = object : HotelComposeViewModel {
        override fun uiState(): StateFlow<UiState> {
            return MutableStateFlow(ErrorUiState("Error message"))
        }
    }
    HotelComposeScreen({}, 1, viewModel = fakeViewModel)
}