package org.company.app.ui.screens.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.company.app.ui.components.AppTimePicker
import org.company.app.ui.components.Progress

data class RoutineScreen(
    val isNight: Boolean,
) : Screen {
    override val key = if (isNight) "Night Routine" else "Morning Routine"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel { RoutineScreenModel() }
        val state by screenModel.state.collectAsState()

        LaunchedEffect(currentCompositeKeyHash) {
            screenModel.getItem()
        }

        when (val response = state) {
            is RoutineUiState.Loading -> Progress()
            is RoutineUiState.Success ->
                RoutineContent(
                    response = response,
                    onUpdateState = {
                        screenModel.updateItem(it)
                    },
                )
            else -> {
            }
        }
    }

    @Composable
    private fun RoutineContent(
        response: RoutineUiState.Success,
        onUpdateState: (Payload) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier.fillMaxSize().padding(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = key,
                    modifier = Modifier.weight(1f),
                )
                Switch(
                    checked = response.payload.isEnabled,
                    onCheckedChange = {
                        val newState =
                            response.payload.copy(
                                isEnabled = it,
                            )
                        onUpdateState(newState)
                    },
                )
            }

            Divider(modifier = modifier.padding(vertical = 8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth(),
            ) {
                Text(text = RoutineUtils.getDayName(response.payload.timeState.day))

                AppTimePicker(
                    timePickerState =
                        TimePickerState(
                            initialHour = response.payload.timeState.hour,
                            initialMinute = response.payload.timeState.minute,
                            is24Hour = true,
                        ),
                )

                Switch(
                    checked = response.payload.timeState.isEnabled,
                    onCheckedChange = {
                        val newState =
                            response.payload.timeState.copy(
                                isEnabled = it,
                            )
                        onUpdateState(
                            response.payload.copy(
                                timeState = newState,
                            ),
                        )
                    },
                )
            }
        }
    }
}