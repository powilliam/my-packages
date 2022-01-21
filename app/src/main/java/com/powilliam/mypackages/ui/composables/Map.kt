package com.powilliam.mypackages.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.MapView
import com.powilliam.mypackages.ui.utils.rememberLifecycleObserver

@Composable
fun Map(modifier: Modifier = Modifier, onMapReady: (MapView) -> Unit = {}) {
    val mapView = rememberMapViewWithLifecycleObserver()
    AndroidView({ mapView }, modifier, onMapReady)
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