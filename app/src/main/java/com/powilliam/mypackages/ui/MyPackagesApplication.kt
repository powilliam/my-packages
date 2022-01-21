package com.powilliam.mypackages.ui

import android.app.Application
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyPackagesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        onInitializeFirebaseConfig()
    }

    private fun onInitializeFirebaseConfig() {
        val remoteConfig = Firebase.remoteConfig
        val settings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }

        remoteConfig.setConfigSettingsAsync(settings)
        remoteConfig.fetchAndActivate()
            .addOnSuccessListener {
                Log.i(
                    "fetchAndActivate",
                    "Successfully fetched and activated"
                )
            }
            .addOnFailureListener { throwable ->
                Log.e(
                    "fetchAndActivate",
                    throwable.message ?: "Failed to fetch and activate remote config"
                )
            }
    }
}