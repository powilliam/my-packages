package com.powilliam.mypackages.ui.composables

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.MapStyleOptions
import com.powilliam.mypackages.ui.utils.rememberLifecycleObserver
import com.powilliam.mypackages.R

@Composable
fun Map(modifier: Modifier = Modifier, onMapReady: (GoogleMap) -> Unit = {}) {
    val context = LocalContext.current

    val mapView = rememberMapViewWithLifecycleObserver()
    val style = rememberMapStyle({ context })

    AndroidView({ mapView }, modifier, update = { view ->
        view.getMapAsync { googleMap ->
            googleMap.setMapStyle(style)
            onMapReady(googleMap)
        }
    })
}

@Composable
fun rememberMapViewWithLifecycleObserver(): MapView {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val view = remember {
        MapView(context)
    }
    val observer = rememberLifecycleObserver(
        onCreate = { view.onCreate(null) },
        onStart = view::onStart,
        onResume = view::onResume,
        onPause = view::onPause,
        onStop = view::onStop,
        onDestroy = view::onDestroy
    )

    DisposableEffect(lifecycleOwner, observer) {
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    return view
}

@Composable
fun rememberMapStyle(
    context: () -> Context,
    isDarkModeEnabled: Boolean = isSystemInDarkTheme()
): MapStyleOptions {
    return remember(isDarkModeEnabled) {
        val json = if (isDarkModeEnabled) R.raw.map_dark_styles else R.raw.map_light_styles
        MapStyleOptions.loadRawResourceStyle(context(), json)
    }
}