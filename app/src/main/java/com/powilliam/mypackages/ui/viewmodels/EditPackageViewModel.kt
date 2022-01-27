package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powilliam.mypackages.ui.validators.PackageValidator
import com.powilliam.mypackages.ui.validators.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditPackageUiState(
    val packageName: String = "",
    val hasValidName: ValidationResult = ValidationResult.Empty
) {
    val canSubmit = listOf(hasValidName).all { field -> field is ValidationResult.Valid }
}

@HiltViewModel
class EditPackageViewModel @Inject constructor(
    private val packageValidator: PackageValidator
) : ViewModel() {
    private val _uiState: MutableStateFlow<EditPackageUiState> =
        MutableStateFlow(EditPackageUiState())
    val uiState: SharedFlow<EditPackageUiState> =
        _uiState.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun onChangePackageName(newValue: String) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                packageName = newValue,
                hasValidName = packageValidator.hasValidName(newValue)
            )
        }
    }
}