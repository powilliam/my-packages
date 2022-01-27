package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditPackageUiState(val packageName: String = "") {
    val canSubmit = listOf(packageName).all { field -> field.isNotEmpty() }
}

@HiltViewModel
class EditPackageViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<EditPackageUiState> =
        MutableStateFlow(EditPackageUiState())
    val uiState: SharedFlow<EditPackageUiState> =
        _uiState.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun onChangePackageName(newValue: String) = viewModelScope.launch {
        _uiState.update { it.copy(packageName = newValue) }
    }
}