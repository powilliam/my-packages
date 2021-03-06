package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powilliam.mypackages.data.entity.Brazil
import com.powilliam.mypackages.data.entity.Coordinates
import com.powilliam.mypackages.data.entity.Package
import com.powilliam.mypackages.data.entity.UserEntity
import com.powilliam.mypackages.data.repository.*
import com.powilliam.mypackages.domain.usecase.CountUnVisualizedNotificationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PackagesMapUiState(
    val account: UserEntity? = null,
    val isAuthFeatureEnabled: Boolean = true,
    val packages: List<Package> = emptyList(),
    val coordinates: Coordinates = Brazil,
    val notificationsCount: Int = 0
) {
    val isSignedIn = account != null
    val shouldPromptSignIn = isSignedIn.not() and isAuthFeatureEnabled
}

@HiltViewModel
class PackagesMapViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val featureFlagRepository: FeatureFlagRepository,
    private val packageRepository: PackageRepository,
    private val userSettingsRepository: UserSettingsRepository,
    private val countUnVisualizedNotificationsUseCase: CountUnVisualizedNotificationsUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<PackagesMapUiState> = MutableStateFlow(
        PackagesMapUiState()
    )
    val uiState: SharedFlow<PackagesMapUiState> =
        _uiState.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    init {
        viewModelScope.launch {
            featureFlagRepository.isAuthFeatureEnabled()
                .distinctUntilChanged()
                .collectLatest { isFeatureEnabled ->
                    _uiState.update { it.copy(isAuthFeatureEnabled = isFeatureEnabled) }
                }
        }

        viewModelScope.launch {
            authRepository.getAuthenticatedAccount()
                .distinctUntilChanged { old, new -> old?.id == new?.id }
                .onEach { account ->
                    account?.let {
                        userSettingsRepository.updateDeviceTokenSettings(it.id)
                    }
                }
                .collectLatest { account ->
                    _uiState.update { it.copy(account = account) }
                }
        }
    }

    fun onCollectPackagesBasedOnSignedAccount() = viewModelScope.launch {
        packageRepository.all()
            .onStart { emit(emptyList()) }
            .collect { packages ->
                val first = packages.firstOrNull()?.events?.firstOrNull { event ->
                    event.location.address.coordinates != null
                }?.location?.address?.coordinates ?: Brazil
                _uiState.update { it.copy(packages = packages, coordinates = first) }
            }
    }

    fun onCollectNotificationsCount(receiverId: String) = viewModelScope.launch {
        countUnVisualizedNotificationsUseCase.execute(receiverId)
            .onStart { emit(0) }
            .collect { count ->
                _uiState.update { it.copy(notificationsCount = count) }
            }
    }

    fun onFocusAtOnePackage(entity: Package) {
        viewModelScope.launch {
            val event = entity.events.firstOrNull { it.location.address.coordinates != null }
            event?.location?.address?.coordinates?.let { coordinates ->
                _uiState.update { it.copy(coordinates = coordinates) }
            }
        }
    }

    fun onAuthenticateWithGoogleSignIn(idToken: String?) = viewModelScope.launch {
        authRepository.authenticateWithGoogleSignIn(idToken)?.let { account ->
            _uiState.update { it.copy(account = account) }
        }
    }

    fun onSignOut() = viewModelScope.launch {
        authRepository.unAuthenticate()
    }
}