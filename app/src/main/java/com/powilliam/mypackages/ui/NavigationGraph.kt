package com.powilliam.mypackages.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.powilliam.mypackages.ui.screens.PackageScreen
import com.powilliam.mypackages.ui.screens.AddPackageScreen
import com.powilliam.mypackages.ui.screens.EditPackageScreen
import com.powilliam.mypackages.ui.screens.PackagesMapScreen
import com.powilliam.mypackages.ui.screens.SearchPackageScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Destination.PackagesMap.route) {
        addPackagesScreen()
    }
}

private fun NavGraphBuilder.addPackagesScreen() {
    composable(route = Destination.PackagesMap.route) {
        PackagesMapScreen()
    }
    composable(route = Destination.Package.route) {
        PackageScreen()
    }
    composable(route = Destination.AddPackage.route) {
        AddPackageScreen()
    }
    composable(route = Destination.EditPackage.route) {
        EditPackageScreen()
    }
    composable(route = Destination.SearchPackage.route) {
        SearchPackageScreen()
    }
}