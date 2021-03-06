package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.powilliam.mypackages.data.entity.Package
import com.powilliam.mypackages.data.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PackageUiState(val entity: Package? = null)

@HiltViewModel
class PackageViewModel @Inject constructor(
    private val packageRepository: PackageRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<PackageUiState> = MutableStateFlow(PackageUiState())
    val uiState: SharedFlow<PackageUiState> =
        _uiState.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun onPopulateScreen(tracker: String) {
        viewModelScope.launch {
            packageRepository.one(tracker)
                .distinctUntilChanged()
                .collectLatest { entity ->
                    _uiState.update { it.copy(entity = entity) }
                }
        }
    }

    fun onDeletePackage() {
        viewModelScope.launch {
            _uiState.value.entity?.let { entity ->
                packageRepository.delete(entity.tracker)
            }
        }
    }
}