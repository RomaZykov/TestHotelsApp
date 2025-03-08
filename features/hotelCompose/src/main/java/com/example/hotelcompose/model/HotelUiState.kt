package com.example.hotelcompose.model

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hotelcompose.R

data class HotelUiState(
    val name: String,
    val stars: Double,
    val address: String,
    val distance: Double,
    val suitesAvailability: List<Int>,
    val imageId: String,
    val imageUrl: String
) : UiState {
    @Composable
    override fun Display(onBackButtonClicked: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            RelatedImage(imageId, imageUrl)
            Title(name, stars)
            Spacer(modifier = Modifier.padding(2.dp))
            SingleLineBlock(address, com.example.ui.R.drawable.pin)
            Spacer(modifier = Modifier.padding(4.dp))
            SingleLineBlock(
                stringResource(R.string.distance_from_center, distance),
                com.example.ui.R.drawable.elbow_connector
            )
            Spacer(modifier = Modifier.padding(4.dp))

            SingleLineBlock(
                stringResource(
                    R.string.specific_suites_available,
                    suitesAvailability.joinToString { it.toString() }
                ),
                com.example.ui.R.drawable.bed_single
            )

            Spacer(modifier = Modifier.weight(1f))

            BackButton(onBackButtonClicked)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun RelatedImage(imageId: String, imageUrl: String) {
    // TODO: Use validation options
    if (imageId.contains("null") || imageId.substringBefore('.')
            .contains(Regex("[а-яА-ЯёЁa-zA-Z]")) || imageId.isBlank()
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.group_4),
            contentDescription = null
        )
    } else {
        GlideImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(200.dp)
                .clip(RoundedCornerShape(size = 16.dp))
                .clip(CutEdgesShape())
        )
    }
}

@Composable
private fun ColumnScope.Title(name: String, stars: Double) {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .align(Alignment.CenterHorizontally)
    ) {
        Text(text = name, textAlign = TextAlign.Center, fontSize = 24.sp)
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = stars.toString(), fontSize = 24.sp)
            Image(
                painter = painterResource(id = com.example.ui.R.drawable.star_small),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun SingleLineBlock(primaryText: String, @DrawableRes image: Int) {
    Row {
        Image(
            modifier = Modifier
                .drawBehind {
                    drawRoundRect(color = Color.Gray, cornerRadius = CornerRadius(8.dp.toPx()))
                }
                .size(32.dp)
                .padding(6.dp),
            painter = painterResource(image),
            contentDescription = null
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = primaryText,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun BackButton(onBackButtonClicked: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonColors(
            colorResource(com.example.theme.R.color.black),
            colorResource(com.example.theme.R.color.white),
            colorResource(com.example.theme.R.color.transparent),
            colorResource(com.example.theme.R.color.transparent)
        ),
        onClick = {
            onBackButtonClicked.invoke()
        }) {
        Text(text = stringResource(R.string.back_button))
    }
}

private class CutEdgesShape(private val dp: Float = 1f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            addRect(
                Rect(
                    left = dp + 2f,
                    top = dp,
                    right = size.width - dp - 3,
                    bottom = size.height - dp
                )
            )
        }
        return Outline.Generic(path = path)
    }
}

@Preview(showSystemUi = true)
@Composable
fun HotelUiStatePreview() {
    HotelUiState(
        name = "Super Duper Deluxe Ultimate Premium Amazing Hotel Name",
        stars = 0.0,
        address = "Brooklyn Avenue 57",
        distance = 157.0,
        suitesAvailability = listOf(1, 7, 95, 63),
        imageId = "",
        imageUrl = ""
    ).Display {}
}
