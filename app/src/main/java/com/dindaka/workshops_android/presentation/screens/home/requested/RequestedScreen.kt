package com.dindaka.workshops_android.presentation.screens.home.requested

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dindaka.workshops_android.R
import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.presentation.components.GeneralScaffold

@Composable
fun RequestedScreen(navController: NavHostController, viewModel: HistoryServiceViewModel = hiltViewModel()) {
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val items: List<ServiceDTO> by viewModel.historyService.observeAsState(emptyList())

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
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    HistoryServiceComponent(
                        null,
                        navController = navController,
                        items = items
                    )
                }
                if (isLoading) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .clickable { }) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    )
}