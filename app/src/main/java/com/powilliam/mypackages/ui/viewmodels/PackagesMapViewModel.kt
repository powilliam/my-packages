package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powilliam.mypackages.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PackagesMapUiState(
    val isSignedIn: Boolean = false,
    val isGettingSignedAccount: Boolean = true
) {
    val canBeginSignIn: Boolean = isSignedIn.not() and isGettingSignedAccount.not()
}

@HiltViewModel
class PackagesMapViewModel @Inject constructor(
    private val authRepository: AuthRepository
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
        }
    }

    fun onAuthenticateWithGoogleSignIn(idToken: String?) = viewModelScope.launch {
        authRepository.authenticateWithGoogleSignIn(idToken)
    }
}