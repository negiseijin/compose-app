package org.company.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import org.company.app.ui.theme.LocalThemeIsDark

@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationClick: (() -> Unit)? = null,
    onActionsClick: (() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (onNavigationClick != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Navigation",
                    )
                }
            }
        },
        actions = {
            if (onActionsClick != null) {
//                IconButton(onClick = onActionsClick) {
//                    Icon(
//                        Icons.Filled.Settings,
//                        contentDescription = "Actions",
//                    )
//                }
                var isDark by LocalThemeIsDark.current
                IconButton(
                    onClick = { isDark = !isDark },
                ) {
                    Icon(
                        imageVector =
                            if (isDark) {
                                Icons.Default.LightMode
                            } else {
                                Icons.Default.DarkMode
                            },
                        contentDescription = null,
                    )
                }
            }
        },
        colors = appTopBarColors,
    )
}

private val appTopBarColors: TopAppBarColors
    @Composable
    get() =
        topAppBarColors(
            containerColor = colorScheme.primary,
            titleContentColor = colorScheme.onPrimary,
            navigationIconContentColor = colorScheme.onPrimary,
            actionIconContentColor = colorScheme.onSecondary,
        )