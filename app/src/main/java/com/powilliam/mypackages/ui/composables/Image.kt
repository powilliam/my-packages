package com.powilliam.mypackages.ui.composables

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter

@Composable
fun NetworkImage(modifier: Modifier = Modifier, uri: Uri) {
    val painter = rememberImagePainter(
        data = uri,
        builder = { crossfade(true) }
    )

    Image(modifier = modifier, painter = painter, contentDescription = null)
}