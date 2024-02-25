package org.company.app.ui.screens.routine

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.company.app.ui.screens.home.dummyData

class RoutineScreenModel : StateScreenModel<RoutineUiState>(RoutineUiState.Loading) {
    fun getItem() {
        screenModelScope.launch {
            try {
                mutableState.value = RoutineUiState.Loading
                delay(1_000)
                mutableState.value =
                    RoutineUiState.Success(RoutineUtils.dummyData())
            } catch (e: Exception) {
                mutableState.value = RoutineUiState.Error
            }
        }
    }

    fun updateItem(request: Payload) {
        mutableState.value = RoutineUiState.Success(request)
    }

    override fun onDispose() {
        println("ScreenModel: dispose details")
    }
}