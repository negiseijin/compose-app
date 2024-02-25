package org.company.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * @see <a href="https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#TimePicker(androidx.compose.material3.TimePickerState,androidx.compose.ui.Modifier,androidx.compose.material3.TimePickerColors,androidx.compose.material3.TimePickerLayoutType)">TimePicker
 * </a>
 */
@Composable
fun AppTimePicker(
    timePickerState: TimePickerState,
    modifier: Modifier = Modifier,
    onCancel: (() -> Unit)? = null,
    onConfirm: ((TimePickerState) -> Unit)? = null,
) {
    var showTimePicker by remember { mutableStateOf(false) }
    val state =
        rememberCustomTimePickerState(
            initialHour = timePickerState.hour,
            initialMinute = timePickerState.minute,
            is24Hour = timePickerState.is24hour,
        )

    Box(propagateMinConstraints = false) {
        TextButton(
            onClick = { showTimePicker = true },
            shape = MaterialTheme.shapes.medium,
        ) {
            Text(
                text = "${state.timeState.hour}:${state.timeState.minute}",
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onCancel = {
                showTimePicker = false
                state.pendingTimeState =
                    TimePickerState(
                        state.timeState.hour,
                        state.timeState.minute,
                        state.timeState.is24hour,
                    )
                onCancel?.invoke()
            },
            onConfirm = {
                showTimePicker = false
                state.timeState =
                    TimePickerState(
                        state.pendingTimeState.hour,
                        state.pendingTimeState.minute,
                        state.pendingTimeState.is24hour,
                    )
                onConfirm?.invoke(state.timeState)
            },
        ) {
            TimePicker(state = state.pendingTimeState)
        }
    }
}

/**
 * @see <a href="https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material3/samples/TimePickerSamples.kt;l=230;drc=03ca30d22e6ee3483142f2e4048db459cb5afb79">TimePickerSamples</a>
 */
@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties =
            DialogProperties(
                usePlatformDefaultWidth = false,
            ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier =
                Modifier
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Min)
                    .background(
                        shape = MaterialTheme.shapes.extraLarge,
                        color = MaterialTheme.colorScheme.surface,
                    ),
        ) {
            toggle()
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                )
                content()
                Row(
                    modifier =
                        Modifier
                            .height(40.dp)
                            .fillMaxWidth(),
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel,
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = onConfirm,
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

private class CustomTimePickerState(
    var timeState: TimePickerState,
    var pendingTimeState: TimePickerState,
) {
    companion object {
        /**
         * The default [Saver] implementation for [CustomTimePickerState].
         */
        fun Saver(): Saver<CustomTimePickerState, *> =
            Saver(
                save = {
                    listOf(
                        it.timeState.hour,
                        it.timeState.minute,
                        it.timeState.is24hour,
                    )
                },
                restore = { value ->
                    CustomTimePickerState(
                        TimePickerState(
                            initialHour = value[0] as Int,
                            initialMinute = value[1] as Int,
                            is24Hour = value[2] as Boolean,
                        ),
                        TimePickerState(
                            initialHour = value[0] as Int,
                            initialMinute = value[1] as Int,
                            is24Hour = value[2] as Boolean,
                        ),
                    )
                },
            )
    }
}

@Composable
private fun rememberCustomTimePickerState(
    initialHour: Int = 0,
    initialMinute: Int = 0,
    is24Hour: Boolean = false,
): CustomTimePickerState {
    return rememberSaveable(
        saver = CustomTimePickerState.Saver(),
    ) {
        CustomTimePickerState(
            TimePickerState(
                initialHour = initialHour,
                initialMinute = initialMinute,
                is24Hour = is24Hour,
            ),
            TimePickerState(
                initialHour = initialHour,
                initialMinute = initialMinute,
                is24Hour = is24Hour,
            ),
        )
    }
}