package org.company.app.ui.screens.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import io.ktor.util.date.WeekDay
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

            LazyColumn {
                items(
                    count = response.payload.timeState.size,
                ) { index ->
                    val item = response.payload.timeState[index]

                    TimeRow(
                        day = item.day,
                        hour = item.hour,
                        minute = item.minute,
                        isEnabled = item.isEnabled,
                        onCheckedChange = { newIsEnabled ->
                            val newTimeState =
                                response.payload.timeState.map { timeState ->
                                    if (timeState.day == item.day) {
                                        timeState.copy(
                                            isEnabled = newIsEnabled,
                                        )
                                    } else {
                                        timeState
                                    }
                                }
                            onUpdateState(
                                response.payload.copy(
                                    timeState = newTimeState,
                                ),
                            )
                        },
                    )
                }
            }
        }
    }

    @Composable
    private fun TimeRow(
        day: WeekDay,
        hour: Int,
        minute: Int,
        isEnabled: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth(),
        ) {
            Text(text = RoutineUtils.getDayName(day))

            AppTimePicker(
                timePickerState =
                    TimePickerState(
                        initialHour = hour,
                        initialMinute = minute,
                        is24Hour = true,
                    ),
            )

            Switch(
                checked = isEnabled,
                onCheckedChange = {
                    onCheckedChange(it)
                },
            )
        }
    }
}