package com.powilliam.mypackages.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.powilliam.mypackages.R
import com.powilliam.mypackages.ui.theming.MyPackagesTheme

class MyPackagesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                Content()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Content() {
        MyPackagesTheme({ this }) {
            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        title = {
                            Text(text = stringResource(R.string.app_name))
                        }
                    )
                },
                content = {}
            )
        }
    }
}