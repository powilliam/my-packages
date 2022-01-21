package com.powilliam.mypackages.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.powilliam.mypackages.ui.composables.Map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackagesMapScreen(modifier: Modifier = Modifier) {
    Scaffold {
        Box {
            Map(
                modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
            )
        }
    }
}