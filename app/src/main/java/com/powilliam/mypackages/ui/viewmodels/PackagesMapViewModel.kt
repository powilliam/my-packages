package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powilliam.mypackages.data.repository.AuthRepository
import com.powilliam.mypackages.data.repository.FeatureFlagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PackagesMapUiState(
    val isSignedIn: Boolean = false,
    val isGettingSignedAccount: Boolean = true,
    val isAuthFeatureEnabled: Boolean = true
) {
    val canBeginSignIn: Boolean =
        isSignedIn.not() and isGettingSignedAccount.not() and isAuthFeatureEnabled
}

@HiltViewModel
class PackagesMapViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val featureFlagRepository: FeatureFlagRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<PackagesMapUiState> =
        MutableStateFlow(PackagesMapUiState())
    val uiState: SharedFlow<PackagesMapUiState> =
        _uiState.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    init {
        viewModelScope.launch {
            authRepository.getAuthenticatedAccount()
                .onStart { _uiState.update { it.copy(isGettingSignedAccount = true) } }
                .onCompletion { _uiState.update { it.copy(isGettingSignedAccount = false) } }
                .collect { user ->
                    _uiState.update { it.copy(isSignedIn = user != null) }
                }

            val isAuthFeatureEnabled = featureFlagRepository.isAuthFeatureEnabled()
            _uiState.update { it.copy(isAuthFeatureEnabled = isAuthFeatureEnabled) }
        }
    }

    fun onAuthenticateWithGoogleSignIn(idToken: String?) = viewModelScope.launch {
        authRepository.authenticateWithGoogleSignIn(idToken)
    }
}