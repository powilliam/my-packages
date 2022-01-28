package com.powilliam.mypackages.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PackagesMapScreen(
    modifier: Modifier = Modifier,
    uiState: PackagesMapUiState,
    onChangeAccount: () -> Unit,
    onSignOut: () -> Unit,
    onNavigateToSearchPackageScreen: () -> Unit,
    onNavigateToAddPackageScreen: () -> Unit,
    onNavigateToPackageScreen: (Package) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetElevation = 2.dp,
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
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-7.149172, -34.966084)))
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(5f), 800, null)
            }
            PackagesMapScreenAppBar(
                account = uiState.account,
                isSignedIn = uiState.isSignedIn,
                onShowBottomSheet = { coroutineScope.launch { sheetState.show() } },
                onNavigateToSearchPackageScreen = onNavigateToSearchPackageScreen,
                onNavigateToAddPackageScreen = onNavigateToAddPackageScreen
            )
            PackagesList(
                packages = uiState.packages,
                onNavigateToPackageScreen = onNavigateToPackageScreen
            )
        }
    }
}

@Composable
private fun BoxScope.PackagesMapScreenAppBar(
    modifier: Modifier = Modifier,
    account: UserEntity? = null,
    isSignedIn: Boolean = false,
    onShowBottomSheet: () -> Unit,
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

@Composable
private fun BottomSheet(
    modifier: Modifier = Modifier,
    onChangeAccount: () -> Unit,
    onSignOut: () -> Unit
) {
    Surface(modifier.navigationBarsPadding()) {
        Column {
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
    }
}

