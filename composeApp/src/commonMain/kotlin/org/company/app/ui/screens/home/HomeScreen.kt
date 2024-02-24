package org.company.app.ui.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey

class HomeScreen : Screen {
    override val key = uniqueScreenKey

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { HomeScreenModel() }

        HomeContent()
    }

    @Composable
    private fun HomeContent() {
        Text(text = "Home")
    }
}