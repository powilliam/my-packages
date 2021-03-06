package com.powilliam.mypackages.ui

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyPackagesApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        onInitializeFirebaseConfig()
        onEnableDiskPersistence()
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder().setWorkerFactory(hiltWorkerFactory).build()

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

    private fun onEnableDiskPersistence() {
        Firebase.database.setPersistenceEnabled(true)
    }

    companion object {
        private const val SIX_HOURS_IN_SECONDS = 21600L
    }
}