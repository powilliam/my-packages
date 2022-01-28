package com.powilliam.mypackages.ui

sealed class Destination(val route: String) {
    object PackagesMap : Destination("/packages")
    object Package : Destination("/packages?tracker={tracker}")
    object AddPackage : Destination("/packages/create")
    object EditPackage : Destination("/packages/edit?packageId={packageId}")
    object SearchPackage : Destination("/packages/search")
}
