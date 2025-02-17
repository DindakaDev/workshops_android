package com.dindaka.workshops_android.presentation.screens.home.requested

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavHostController
import com.dindaka.workshops_android.R
import com.dindaka.workshops_android.presentation.components.GeneralScaffold
import com.dindaka.workshops_android.presentation.theme.Workshops_androidTheme

@Composable
fun RequestedScreen(navController: NavHostController) {
    GeneralScaffold(
        title = R.string.title_solicitud,
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        container = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                HistoryServiceComponent("requested", navController = navController)
            }
        }
    )
}