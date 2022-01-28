package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powilliam.mypackages.data.entity.Package
import com.powilliam.mypackages.data.entity.UserEntity
import com.powilliam.mypackages.data.repository.AuthRepository
import com.powilliam.mypackages.data.repository.FeatureFlagRepository
import com.powilliam.mypackages.data.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PackagesMapUiState(
    val account: UserEntity? = null,
    val isAuthFeatureEnabled: Boolean = true,
    val packages: List<Package> = emptyList()
) {
    val isSignedIn = account != null
    val shouldPromptSignIn = isSignedIn.not() and isAuthFeatureEnabled
}

@HiltViewModel
class PackagesMapViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val featureFlagRepository: FeatureFlagRepository,
    private val packageRepository: PackageRepository
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
                .collectLatest { account ->
                    _uiState.update { it.copy(account = account) }
                }
        }

        viewModelScope.launch {
            packageRepository.all()
                .collect { packages ->
                    _uiState.update { it.copy(packages = packages) }
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