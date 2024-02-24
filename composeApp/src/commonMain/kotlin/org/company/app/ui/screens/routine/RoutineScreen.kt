package org.company.app.ui.screens.routine

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

data class RoutineScreen(
    val isNight: Boolean,
) : Screen {
    override val key = if (isNight) "Night Routine" else "Morning Routine"

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { RoutineScreenModel() }

        RoutineContent(isNight = isNight)
    }

    @Composable
    private fun RoutineContent(isNight: Boolean) {
        Text(text = key)
    }
}