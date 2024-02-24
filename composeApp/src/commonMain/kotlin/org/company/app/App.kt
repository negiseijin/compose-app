package org.company.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.company.app.ui.components.AppTopBar
import org.company.app.ui.screens.home.HomeScreen
import org.company.app.ui.theme.AppTheme

@Composable
internal fun App() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Navigator(HomeScreen()) { navigator ->
                Scaffold(
                    topBar = {
                        AppTopBar(
                            title = navigator.lastItem.key,
                            onNavigationClick =
                                if (navigator.lastItem.key == "Home") {
                                    null
                                } else {
                                    { navigator.pop() }
                                },
                            onActionsClick = {},
                        )
                    },
                ) { paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues),
                    ) {
                        SlideTransition(navigator)
                    }
                }
            }
        }
    }
}

internal expect fun openUrl(url: String?)