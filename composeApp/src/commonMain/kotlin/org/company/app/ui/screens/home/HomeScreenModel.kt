package org.company.app.ui.screens.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreenModel : StateScreenModel<HomeUiState>(HomeUiState.Loading) {
    fun getItem() {
        screenModelScope.launch {
            try {
                mutableState.value = HomeUiState.Loading
                delay(1_000)
                mutableState.value =
                    HomeUiState.Success(dummyData())
            } catch (e: Exception) {
                mutableState.value = HomeUiState.Error
            }
        }
    }

    fun updateItem(request: Payload) {
        mutableState.value = HomeUiState.Success(request)
    }

    override fun onDispose() {
        println("ScreenModel: dispose details")
    }
}

fun dummyData(): Payload {
    return Payload(
        isEnabled = true,
    )
}