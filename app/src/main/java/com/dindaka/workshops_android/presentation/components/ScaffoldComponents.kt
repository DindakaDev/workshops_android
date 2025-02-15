package com.dindaka.workshops_android.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralScaffold(
    @StringRes title: Int,
    navigationIcon: @Composable () -> Unit = {},
    container: @Composable () -> Unit = {},
    actions: @Composable() (RowScope.() -> Unit) = {}
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                stringResource(title),
                style = MaterialTheme.typography.titleLarge,
            )
        },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            navigationIcon = {
                navigationIcon.invoke()
            },
            actions = actions)
    }, containerColor = MaterialTheme.colorScheme.surfaceContainerLowest, content = { padding ->
        Column(
            Modifier
                .padding(padding)
        ) {
            container.invoke()
        }
    })
}