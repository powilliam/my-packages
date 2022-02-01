package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powilliam.mypackages.data.entity.Package
import com.powilliam.mypackages.data.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchPackageUiState(
    val query: String = "",
    val packages: List<Package> = emptyList()
)

@HiltViewModel
class SearchPackageViewModel @Inject constructor(
    private val packageRepository: PackageRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<SearchPackageUiState> = MutableStateFlow(
        SearchPackageUiState()
    )
    val uiState: SharedFlow<SearchPackageUiState> =
        _uiState.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun onCollectPackagesBasedOnSignedAccount() = viewModelScope.launch {
        packageRepository.all()
            .onStart { emit(emptyList()) }
            .combine(_uiState) { packages, state ->
                if (state.query.isEmpty().not()) {
                    packages.filter {
                        listOf(it.name, it.tracker)
                            .map { field -> field.uppercase() }
                            .any { field ->
                                field.contains(
                                    state.query.uppercase()
                                )
                            }
                    }
                } else {
                    packages
                }
            }
            .collect { packages ->
                _uiState.update { it.copy(packages = packages) }
            }
    }

    fun onSearch(newQuery: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(query = newQuery) }
        }
    }
}