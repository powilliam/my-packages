package com.powilliam.mypackages.ui.theming

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.powilliam.mypackages.R

val NotoSansFontFamily =
    FontFamily(
        Font(resId = R.font.notosans_regular, weight = FontWeight.Normal),
        Font(resId = R.font.notosans_medium, weight = FontWeight.Medium)
    )

val NotoSansTypography = Typography(
    displayLarge = TextStyle(
        fontSize = 57.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = NotoSansFontFamily,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontSize = 45.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = NotoSansFontFamily,
        lineHeight = 52.sp,
    ),
    displaySmall = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = NotoSansFontFamily,
        lineHeight = 44.sp,
    ),
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = NotoSansFontFamily,
        lineHeight = 40.sp,
    ),
    headlineMedium = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = NotoSansFontFamily,
        lineHeight = 36.sp,
    ),
    headlineSmall = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = NotoSansFontFamily,
        lineHeight = 32.sp,
    ),
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = NotoSansFontFamily,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = NotoSansFontFamily,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = NotoSansFontFamily,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = NotoSansFontFamily,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = NotoSansFontFamily,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = NotoSansFontFamily,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = NotoSansFontFamily,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = NotoSansFontFamily,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = NotoSansFontFamily,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    )
)