package com.dindaka.workshops_android.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dindaka.workshops_android.R
import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.presentation.components.BodyTextComponent
import com.dindaka.workshops_android.presentation.components.FilterDialog
import com.dindaka.workshops_android.presentation.components.GeneralScaffold
import com.dindaka.workshops_android.presentation.navigation.Routes
import com.dindaka.workshops_android.presentation.screens.home.requested.HistoryServiceComponent
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val items: List<ServiceDTO> by viewModel.historyService.observeAsState(emptyList())
    val name: String by viewModel.name.observeAsState("")
    val courotineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val showFilterDialog: Boolean by viewModel.showFilterDialog.observeAsState(false)

    ModalNavigationDrawer( //Drawer
        drawerState = drawerState,
        drawerContent = {
            DrawerGeneral(logout = {
                viewModel.clearLocalData()
                navController.navigate(Routes.Login.route) {
                    popUpTo(Routes.Home.route) { inclusive = true }
                }
            }, name = name) {
                courotineScope.launch {
                    drawerState.close()
                }
            }
        }
    ) {
        GeneralScaffold(
            title = R.string.menu,
            navigationIcon = {
                IconButton(onClick = {
                    courotineScope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Drawer")
                }
            },
            actions = {
                Row {
                    IconButton(onClick = {
                        viewModel.showOrHideFilterDialog()
                    }) {
                        Icon(Icons.Filled.FilterList, contentDescription = "Filter")
                    }
                }
            },
            container = {
                if (showFilterDialog) {
                    FilterDialog(onDismiss = { viewModel.showOrHideFilterDialog() }) { name, type, dates, order ->
                        viewModel.showOrHideFilterDialog()
                    }
                }
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                        ) {
                            ActionsComponent(viewModel, navController)
                            Spacer(Modifier.size(8.dp))
                            HistoryServiceComponent(
                                title = stringResource(R.string.lbl_history_services),
                                items = items,
                                navController = navController
                            )
                        }
                        SyncParts(viewModel)
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
}

@Composable
fun ActionsComponent(viewModel: HomeViewModel, navController: NavHostController) {
    val pendingRequest: Int by viewModel.countPendingService.observeAsState(0)
    Row(
        Modifier
            .height(100.dp)
            .fillMaxWidth()
    ) {
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp)
                .clickable { navController.navigate(Routes.ApplyPart.route) }
        ) {
            Column(
                Modifier
                    .align(Alignment.Center)
            ) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.lbl_new_service),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    stringResource(R.string.lbl_new_service),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Spacer(Modifier.size(8.dp))
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .clickable { navController.navigate(Routes.RequestedList.route) }
        ) {
            Column(Modifier.align(Alignment.Center)) {
                Text(
                    stringResource(R.string.lbl_request_parts),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    pendingRequest.toString(),
                    fontSize = 29.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SyncParts(viewModel: HomeViewModel) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable { viewModel.onUserSyncStorage() }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.img_sync),
                contentDescription = "Sync",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondary),
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterHorizontally)
            )
            BodyTextComponent("Sync", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}