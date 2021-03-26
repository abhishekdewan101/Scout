package com.abhishek101.gametracker.ui.components.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.abhishek101.gametracker.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class MainNavigatorKtTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainNavigatorShowsSplashScreenByDefault() {
        composeRule.setContent {
            MainNavigator()
        }

        composeRule.onNodeWithTag("SplashScreen").assertIsDisplayed()
        composeRule.onNodeWithTag("OnBoardingScreen").assertDoesNotExist()
    }
}