package org.company.app.ui.screens.routine

import io.ktor.util.date.WeekDay

sealed class RoutineUiState {
    data object Init : RoutineUiState()

    data object Loading : RoutineUiState()

    data class Success(val payload: Payload) : RoutineUiState()

    data object Error : RoutineUiState()
}

data class Payload(
    val isEnabled: Boolean = false,
    val timeState: List<TimeState>,
)

data class TimeState(
    val day: WeekDay,
    val hour: Int = 8,
    val minute: Int = 30,
    val isEnabled: Boolean = false,
)