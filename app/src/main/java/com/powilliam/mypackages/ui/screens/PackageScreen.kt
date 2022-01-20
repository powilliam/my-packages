package com.powilliam.mypackages.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.powilliam.mypackages.ui.theming.MyPackagesTheme

@Composable
fun PackageScreen() {
}

@Preview
@Composable
private fun PackageScreenPreview() {
    val context = LocalContext.current

    MyPackagesTheme({ context }) {
        PackageScreen()
    }
}