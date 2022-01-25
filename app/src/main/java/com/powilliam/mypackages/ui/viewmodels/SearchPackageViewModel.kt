package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchPackageUiState(val query: String = "")

@HiltViewModel
class SearchPackageViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<SearchPackageUiState> = MutableStateFlow(
        SearchPackageUiState()
    )
    val uiState: SharedFlow<SearchPackageUiState> =
        _uiState.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun onSearch(newQuery: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(query = newQuery) }
        }
    }
}