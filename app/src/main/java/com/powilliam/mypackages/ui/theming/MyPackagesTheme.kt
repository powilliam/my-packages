package com.powilliam.mypackages.ui.theming

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import com.powilliam.mypackages.constants.SystemFeatures

@Composable
fun MyPackagesTheme(
    context: () -> Context,
    isDynamicColorSchemeSupported: Boolean = SystemFeatures.isDynamicColorSchemeSupported,
    isDarkModeEnabled: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        isDynamicColorSchemeSupported && isDarkModeEnabled -> dynamicDarkColorScheme(context())
        isDynamicColorSchemeSupported && !isDarkModeEnabled -> dynamicLightColorScheme(context())
        !isDynamicColorSchemeSupported && isDarkModeEnabled -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(colorScheme, NotoSansTypography, content)
}