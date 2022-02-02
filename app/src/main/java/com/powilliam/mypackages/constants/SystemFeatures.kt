package com.powilliam.mypackages.constants

import android.os.Build

object SystemFeatures {
    val isDynamicColorSchemeSupported: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}