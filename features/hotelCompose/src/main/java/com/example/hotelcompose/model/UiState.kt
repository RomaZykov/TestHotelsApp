package com.example.hotelcompose.model

import androidx.compose.runtime.Composable

interface UiState {
    @Composable
    fun Display(onBackButtonClicked: () -> Unit)
}