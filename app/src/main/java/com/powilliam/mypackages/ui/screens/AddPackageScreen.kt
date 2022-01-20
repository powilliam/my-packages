package com.powilliam.mypackages.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.powilliam.mypackages.ui.theming.MyPackagesTheme

@Composable
fun AddPackageScreen() {
}

@Preview
@Composable
private fun AddPackageScreenPreview() {
    val context = LocalContext.current

    MyPackagesTheme({ context }) {
        AddPackageScreen()
    }
}
