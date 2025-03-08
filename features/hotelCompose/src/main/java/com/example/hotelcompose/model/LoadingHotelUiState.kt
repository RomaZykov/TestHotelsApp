package com.example.hotelcompose.model

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

object LoadingHotelUiState : UiState {
    @Composable
    override fun Display(onBackButtonClicked: () -> Unit) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(180.dp),
                color = colorResource(com.example.theme.R.color.gray),
                strokeWidth = 16.dp,
                strokeCap = StrokeCap.Round
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoadingHotelUiStatePreview() {
    LoadingHotelUiState.Display {}
}
