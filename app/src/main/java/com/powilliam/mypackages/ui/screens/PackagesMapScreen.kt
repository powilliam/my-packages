package com.powilliam.mypackages.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Badge
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.powilliam.mypackages.data.entity.*
import com.powilliam.mypackages.ui.composables.*
import com.powilliam.mypackages.ui.viewmodels.PackagesMapUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val HALF_OF_SECOND_IN_MILLISECONDS = 500L
private const val ONE_SECOND_IN_MILLISECONDS = 1000

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PackagesMapScreen(
    modifier: Modifier = Modifier,
    uiState: PackagesMapUiState,
    onChangeAccount: () -> Unit,
    onSignOut: () -> Unit,
    onFocusAtOnePackage: (Package) -> Unit,
    onNavigateToSearchPackageScreen: () -> Unit,
    onNavigateToAddPackageScreen: () -> Unit,
    onNavigateToPackageScreen: (Package) -> Unit,
    onNavigateToNotificationsScreen: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var googleMapRef: GoogleMap? by remember { mutableStateOf(null) }

    LaunchedEffect(uiState.coordinates, googleMapRef) {
        snapshotFlow { uiState.coordinates }
            .map {
                val isBrazil = listOf(Brazil.latitude, Brazil.longitude).all { coordinate ->
                    listOf(it.latitude, it.longitude).contains(coordinate)
                }
                val target = LatLng(it.latitude, it.longitude)
                val zoom = if (isBrazil) 2f else 10f
                CameraPosition.Builder()
                    .target(target)
                    .zoom(zoom)
                    .build()
            }
            .collect { position ->
                delay(HALF_OF_SECOND_IN_MILLISECONDS)
                googleMapRef?.animateCamera(
                    CameraUpdateFactory.newCameraPosition(position),
                    ONE_SECOND_IN_MILLISECONDS,
                    null
                )
            }
    }

    LaunchedEffect(uiState.packages, googleMapRef) {
        snapshotFlow { uiState.packages }
            .onStart { googleMapRef?.clear() }
            .collect { packages ->
                packages.forEach { entity ->
                    entity.events.firstOrNull()?.let { event ->
                        event.location.address.coordinates?.let { coordinates ->
                            val position = LatLng(coordinates.latitude, coordinates.longitude)
                            val marker = MarkerOptions()
                                .position(position)
                                .title(entity.tracker)
                                .snippet(entity.name)
                            googleMapRef?.addMarker(marker)
                        }
                    }
                }
            }
    }

    ModalBottomSheet(
        state = sheetState,
        sheetContent = {
            BottomSheet(
                onChangeAccount = {
                    coroutineScope.launch {
                        onChangeAccount()
                        sheetState.hide()
                    }
                },
                onSignOut = {
                    coroutineScope.launch {
                        onSignOut()
                        sheetState.hide()
                    }
                }
            )
        }
    ) {
        Box {
            Map(
                modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
            ) { googleMap ->
                googleMapRef = googleMap
            }
            PackagesMapScreenAppBar(
                account = uiState.account,
                isSignedIn = uiState.isSignedIn,
                notificationsCount = uiState.notificationsCount,
                onShowBottomSheet = { coroutineScope.launch { sheetState.show() } },
                onNavigateToSearchPackageScreen = onNavigateToSearchPackageScreen,
                onNavigateToNotificationsScreen = onNavigateToNotificationsScreen,
                onNavigateToAddPackageScreen = onNavigateToAddPackageScreen
            )
            PackagesList(
                packages = uiState.packages,
                onNavigateToPackageScreen = onNavigateToPackageScreen,
                onFocusAtOnePackage = onFocusAtOnePackage
            )
        }
    }
}

@Composable
private fun BoxScope.PackagesMapScreenAppBar(
    modifier: Modifier = Modifier,
    account: UserEntity? = null,
    isSignedIn: Boolean = false,
    notificationsCount: Int = 0,
    onShowBottomSheet: () -> Unit,
    onNavigateToSearchPackageScreen: () -> Unit,
    onNavigateToNotificationsScreen: () -> Unit,
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
                    Avatar(onClick = onShowBottomSheet) {
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
            IconButton(onClick = onNavigateToNotificationsScreen) {
                BadgedBox(
                    badge = {
                        if (notificationsCount > 0) {
                            Badge { Text(text = "$notificationsCount") }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            IconButton(onClick = onNavigateToAddPackageScreen) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun BoxScope.PackagesList(
    modifier: Modifier = Modifier,
    packages: List<Package> = emptyList(),
    onFocusAtOnePackage: (Package) -> Unit,
    onNavigateToPackageScreen: (Package) -> Unit
) {
    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState, packages) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            val entity = packages.getOrNull(page)
            if (entity != null) onFocusAtOnePackage(entity)
        }
    }

    HorizontalPager(
        state = pagerState,
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

@Composable
private fun BottomSheet(
    modifier: Modifier = Modifier,
    onChangeAccount: () -> Unit,
    onSignOut: () -> Unit
) {
    TextButton(onClick = onChangeAccount) {
        Icon(
            modifier = modifier.size(ButtonDefaults.IconSize),
            imageVector = Icons.Rounded.AccountCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier.width(ButtonDefaults.IconSpacing))
        Text(text = "Trocar de conta")
    }
    TextButton(onClick = onSignOut) {
        Icon(
            modifier = modifier.size(ButtonDefaults.IconSize),
            imageVector = Icons.Rounded.Logout,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier.width(ButtonDefaults.IconSpacing))
        Text(text = "Sair")
    }
}

