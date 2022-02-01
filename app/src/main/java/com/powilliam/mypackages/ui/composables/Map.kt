package com.powilliam.mypackages.ui.composables

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.powilliam.mypackages.ui.utils.rememberLifecycleObserver
import com.powilliam.mypackages.R
import com.powilliam.mypackages.data.entity.Brazil

@Composable
fun Map(
    modifier: Modifier = Modifier,
    options: GoogleMapOptions = GoogleMapOptions()
        .backgroundColor(MaterialTheme.colorScheme.background.toArgb())
        .mapToolbarEnabled(false),
    onMapReady: (GoogleMap) -> Unit = {}
) {
    val context = LocalContext.current

    val mapView = rememberMapViewWithLifecycleObserver(options)
    val style = rememberMapStyle({ context })

    AndroidView({ mapView }, modifier, update = { view ->
        view.getMapAsync { googleMap ->
            googleMap.setMapStyle(style)
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLng(
                    LatLng(Brazil.latitude, Brazil.longitude)
                )
            )
            onMapReady(googleMap)
        }
    })
}

@Composable
fun rememberMapViewWithLifecycleObserver(options: GoogleMapOptions = GoogleMapOptions()): MapView {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val view = remember {
        MapView(context, options)
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