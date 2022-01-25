package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powilliam.mypackages.data.entity.UserEntity
import com.powilliam.mypackages.data.repository.AuthRepository
import com.powilliam.mypackages.data.repository.FeatureFlagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PackagesMapUiState(
    val account: UserEntity? = null,
    val isGettingSignedAccount: Boolean = true,
    val isAuthFeatureEnabled: Boolean = true
) {
    val isSignedIn = account != null
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
                .collect { account ->
                    _uiState.update { it.copy(account = account) }
                }

            val isAuthFeatureEnabled = featureFlagRepository.isAuthFeatureEnabled()
            _uiState.update { it.copy(isAuthFeatureEnabled = isAuthFeatureEnabled) }
        }
    }

    fun onAuthenticateWithGoogleSignIn(idToken: String?) = viewModelScope.launch {
        authRepository.authenticateWithGoogleSignIn(idToken)
    }
}