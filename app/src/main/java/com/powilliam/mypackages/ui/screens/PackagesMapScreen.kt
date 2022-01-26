package com.powilliam.mypackages.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.powilliam.mypackages.data.entity.*
import com.powilliam.mypackages.ui.composables.Avatar
import com.powilliam.mypackages.ui.composables.Map
import com.powilliam.mypackages.ui.composables.NetworkImage
import com.powilliam.mypackages.ui.composables.PackageOnMapCard
import com.powilliam.mypackages.ui.viewmodels.PackagesMapUiState

@Composable
fun PackagesMapScreen(
    modifier: Modifier = Modifier,
    uiState: PackagesMapUiState,
    onNavigateToSearchPackageScreen: () -> Unit,
    onNavigateToAddPackageScreen: () -> Unit,
    onNavigateToPackageScreen: (Package) -> Unit
) {
    Box {
        Map(
            modifier
                .fillMaxSize()
                .align(Alignment.Center),
        ) { googleMap ->
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-7.149172, -34.966084)))
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(5f), 800, null)
        }
        PackagesMapScreenAppBar(
            account = uiState.account,
            isSignedIn = uiState.isSignedIn,
            onNavigateToSearchPackageScreen = onNavigateToSearchPackageScreen,
            onNavigateToAddPackageScreen = onNavigateToAddPackageScreen
        )
        PackagesList(
            packages = listOf(
                Package(
                    name = "Meu pacote", tracker = "OQ143631625BR", events = listOf(
                        Event(
                            type = EventType.BDE,
                            description = "Objeto entregue ao destinatário",
                            location = Location(
                                type = "Agência dos Correios",
                                address = Address(city = "Humaita", province = "AM"),
                            )
                        )
                    )
                )
            ),
            onNavigateToPackageScreen = onNavigateToPackageScreen
        )
    }
}

@Composable
private fun BoxScope.PackagesMapScreenAppBar(
    modifier: Modifier = Modifier,
    account: UserEntity? = null,
    isSignedIn: Boolean = false,
    onNavigateToSearchPackageScreen: () -> Unit,
    onNavigateToAddPackageScreen: () -> Unit
) {
    SmallTopAppBar(
        modifier = modifier
            .statusBarsPadding()
            .align(Alignment.TopCenter),
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            if (isSignedIn) {
                account?.avatar?.let {
                    // TODO: Show initials if there's no image to display
                    Avatar {
                        NetworkImage(modifier = modifier.fillMaxSize(), uri = it)
                    }
                }
            }
        },
        title = {},
        actions = {
            IconButton(onClick = onNavigateToSearchPackageScreen) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            if (isSignedIn) {
                IconButton(onClick = onNavigateToAddPackageScreen) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun BoxScope.PackagesList(
    modifier: Modifier = Modifier,
    packages: List<Package> = emptyList(),
    onNavigateToPackageScreen: (Package) -> Unit
) {
    HorizontalPager(
        modifier = modifier
            .navigationBarsPadding()
            .align(Alignment.BottomCenter),
        count = packages.count()
    ) { position ->
        PackageOnMapCard(
            entity = packages[position],
            onClick = onNavigateToPackageScreen
        )
    }
}

