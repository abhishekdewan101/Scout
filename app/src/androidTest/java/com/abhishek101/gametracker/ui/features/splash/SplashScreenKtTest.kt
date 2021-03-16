package com.abhishek101.gametracker.ui.features.splash

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.abhishek101.gametracker.ui.MainActivity
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class SplashScreenKtTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val navigateToHomeScreen: () -> Unit = mockk(relaxed = true)
    private val isAuthenticationValid = mutableStateOf(false)
    private val splashViewModel: SplashViewModel = mockk() {
        every { isAuthenticationValid } returns this@SplashScreenKtTest.isAuthenticationValid
        every { checkAuthentication() } just runs
    }

    @Test
    fun splashScreenRendersTextAndButton() {
        composeRule.setContent {
            SplashScreen(
                navigateToPlatformScreen = navigateToHomeScreen,
                viewModel = splashViewModel
            )
        }
        composeRule.onNodeWithText("GameTracker").assertIsDisplayed()
        composeRule.onNodeWithTag("loadingBar").assertIsDisplayed()
    }

    @Test
    fun splashScreenNavigatesToHomeScreenIfAuthenticationIsValid() {
        isAuthenticationValid.value = true
        composeRule.setContent {
            SplashScreen(
                navigateToPlatformScreen = navigateToHomeScreen,
                viewModel = splashViewModel
            )
        }

        verify {
            navigateToHomeScreen()
        }
    }
}
