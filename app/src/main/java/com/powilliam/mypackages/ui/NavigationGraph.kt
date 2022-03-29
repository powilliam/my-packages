package com.powilliam.mypackages.ui

import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.powilliam.mypackages.ui.routes.*

@Composable
fun NavigationGraph(beginSignIn: () -> IntentSenderRequest) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Destination.PackagesMap.route) {
        addPackagesScreen(navController) { beginSignIn() }
    }
}

private fun NavGraphBuilder.addPackagesScreen(
    navController: NavController,
    beginSignIn: () -> IntentSenderRequest
) {
    composable(route = Destination.PackagesMap.route) {
        PackagesMapRoute(
            { navController },
            beginSignIn = beginSignIn,
        )
    }

    composable(
        route = Destination.Package.route,
        arguments = listOf(
            navArgument("tracker") {
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->
        PackageRoute(
            { navController },
            navBackStackEntry = navBackStackEntry,
        )
    }

    composable(route = Destination.AddPackage.route) {
        AddPackageRoute { navController }
    }

    composable(
        route = Destination.EditPackage.route,
        arguments = listOf(
            navArgument("tracker") { type = NavType.StringType },
            navArgument("name") { type = NavType.StringType }
        )
    ) { navBackStackEntry ->
        EditPackageRoute(
            { navController },
            navBackStackEntry = navBackStackEntry,
        )
    }

    composable(route = Destination.SearchPackage.route) {
        SearchPackageRoute {
            navController
        }
    }

    composable(route = Destination.Notifications.route) {
        NotificationsRoute {
            navController
        }
    }
}