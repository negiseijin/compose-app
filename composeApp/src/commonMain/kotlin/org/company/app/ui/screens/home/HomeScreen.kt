package org.company.app.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import org.company.app.ui.components.Progress
import org.company.app.ui.screens.routine.RoutineScreen

class HomeScreen : Screen {
    override val key = "Home"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel { HomeScreenModel() }
        val state by screenModel.state.collectAsState()

        LaunchedEffect(currentCompositeKeyHash) {
            screenModel.getItem()
        }

        when (val response = state) {
            is HomeUiState.Loading -> Progress()
            is HomeUiState.Success ->
                HomeContent(
                    response = response,
                    onUpdateState = {
                        screenModel.updateItem(it)
                    },
                    onClick = { navigator.push(RoutineScreen(isNight = true)) },
                )
            else -> {
            }
        }
    }

    @Composable
    private fun HomeContent(
        response: HomeUiState.Success,
        onUpdateState: (Payload) -> Unit,
        onClick: () -> Unit,
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
                    text = "scenario",
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

            Button(
                onClick = onClick,
            ) {
                Text("Night Routine")
            }
        }
    }
}