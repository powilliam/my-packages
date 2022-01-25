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
            minimumFetchIntervalInSeconds = SIX_HOURS_IN_SECONDS
        }

        remoteConfig.setConfigSettingsAsync(settings)
        remoteConfig.fetchAndActivate()
            .addOnSuccessListener {
                Log.i(
                    "fetchAndActivate",
                    "Successfully fetched and activated. hasFetchedFromRemoteSource: $it"
                )
            }
            .addOnFailureListener { throwable ->
                Log.e(
                    "fetchAndActivate",
                    throwable.message ?: "Failed to fetch and activate remote config"
                )
            }
    }

    companion object {
        private const val SIX_HOURS_IN_SECONDS = 21600L
    }
}