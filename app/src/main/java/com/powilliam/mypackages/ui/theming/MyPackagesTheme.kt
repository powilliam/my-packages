package com.powilliam.mypackages.ui.theming

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.powilliam.mypackages.constants.SystemFeatures

@Composable
fun MyPackagesTheme(
    context: () -> Context,
    isDynamicColorSchemeSupported: Boolean = SystemFeatures.isDynamicColorSchemeSupported,
    isDarkModeEnabled: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colorScheme = when {
        isDynamicColorSchemeSupported && isDarkModeEnabled -> dynamicDarkColorScheme(context())
        isDynamicColorSchemeSupported && !isDarkModeEnabled -> dynamicLightColorScheme(context())
        !isDynamicColorSchemeSupported && isDarkModeEnabled -> DarkColorScheme
        else -> LightColorScheme
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !isDarkModeEnabled
        )
    }

    MaterialTheme(colorScheme, NotoSansTypography, content)
}