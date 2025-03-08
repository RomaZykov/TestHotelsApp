package com.example.hotelcompose.model

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

data class ErrorUiState(val message: String) : UiState {
    @Composable
    override fun Display(onBackButtonClicked: () -> Unit) {
        Text(message)
    }
}
