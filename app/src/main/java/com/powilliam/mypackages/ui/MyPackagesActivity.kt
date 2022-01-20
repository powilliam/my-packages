package com.powilliam.mypackages.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.powilliam.mypackages.R
import com.powilliam.mypackages.ui.theming.MyPackagesTheme

class MyPackagesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        MyPackagesTheme({ this }) {
            Surface(Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}