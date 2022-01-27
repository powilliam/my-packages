package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powilliam.mypackages.data.entity.Package
import com.powilliam.mypackages.data.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddPackageUiState(val packageName: String = "", val packageTracker: String = "") {
    val canSubmit = listOf(packageName, packageTracker).all { field -> field.isNotEmpty() }
}

sealed class FormField {
    object PackageName : FormField()
    object PackageTracker : FormField()
}

@HiltViewModel
class AddPackageViewModel @Inject constructor(
    private val packageRepository: PackageRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<AddPackageUiState> =
        MutableStateFlow(AddPackageUiState())
    val uiState: SharedFlow<AddPackageUiState> =
        _uiState.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun onChangeFormFieldValue(field: FormField, newValue: String) = viewModelScope.launch {
        _uiState.update {
            when (field) {
                FormField.PackageName -> it.copy(packageName = newValue)
                FormField.PackageTracker -> it.copy(packageTracker = newValue)
            }
        }
    }

    fun onSubmit() = viewModelScope.launch {
        packageRepository.insert(
            Package(
                name = _uiState.value.packageName,
                tracker = _uiState.value.packageTracker
            )
        )
    }
}