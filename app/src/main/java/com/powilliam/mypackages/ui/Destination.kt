package com.powilliam.mypackages.ui

sealed class Destination(val route: String) {
    object Package : Destination("/packages?tracker={tracker}")
    object AddPackage : Destination("/packages/create")
    object EditPackage : Destination("/packages/edit?tracker={tracker}&name={name}")
    object Notifications : Destination("/notifications")
}
