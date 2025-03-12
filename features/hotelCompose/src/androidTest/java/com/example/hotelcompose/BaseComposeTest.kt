package com.example.hotelcompose

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule

abstract class BaseComposeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    protected val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    protected fun getString(@StringRes stringRes: Int): String = context.getString(stringRes)

    protected fun getPlurals(@PluralsRes pluralsRes: Int, quantity: Int): String = getPlurals(pluralsRes, quantity)
}