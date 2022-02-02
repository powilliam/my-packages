package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powilliam.mypackages.data.repository.PackageRepository
import com.powilliam.mypackages.ui.validators.PackageValidator
import com.powilliam.mypackages.ui.validators.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditPackageUiState(
    val packageTracker: String = "",
    val packageName: String = "",
    val hasValidName: ValidationResult = ValidationResult.Empty
) {
    val canSubmit = listOf(hasValidName).all { field -> field is ValidationResult.Valid }
}

@HiltViewModel
class EditPackageViewModel @Inject constructor(
    private val packageValidator: PackageValidator,
    private val packageRepository: PackageRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<EditPackageUiState> =
        MutableStateFlow(EditPackageUiState())
    val uiState: SharedFlow<EditPackageUiState> =
        _uiState.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun onPopulateScreen(name: String, tracker: String) = viewModelScope.launch {
        _uiState.update { it.copy(packageName = name, packageTracker = tracker) }
    }

    fun onChangePackageName(newValue: String) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                packageName = newValue,
                hasValidName = packageValidator.hasValidName(newValue)
            )
        }
    }

    fun onSubmit() = viewModelScope.launch {
        packageRepository.update(_uiState.value.packageTracker, _uiState.value.packageName)
    }
}