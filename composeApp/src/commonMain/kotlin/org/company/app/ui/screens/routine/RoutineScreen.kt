package org.company.app.ui.screens.routine

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey

data class RoutineScreen(
    val isNight: Boolean,
) : Screen {
    override val key = uniqueScreenKey

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { RoutineScreenModel() }

        RoutineContent()
    }

    @Composable
    private fun RoutineContent() {
        Text(text = "Routine")
    }
}