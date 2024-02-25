package org.company.app.ui.screens.home

sealed class HomeUiState {
    data object Init : HomeUiState()

    data object Loading : HomeUiState()

    data class Success(val payload: Payload) : HomeUiState()

    data object Error : HomeUiState()
}

data class Payload(
    val isEnabled: Boolean = false,
)