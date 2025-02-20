package com.dindaka.workshops_android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.presentation.screens.home.HomeScreen
import com.dindaka.workshops_android.presentation.screens.home.apply_part.ApplyPartScreen
import com.dindaka.workshops_android.presentation.screens.home.requested.RequestedScreen
import com.dindaka.workshops_android.presentation.screens.home.supply_part.SupplyPartScreen
import com.dindaka.workshops_android.presentation.screens.login.LoginScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Login.route) { LoginScreen(navController) }
        composable(Routes.Home.route) { HomeScreen(navController) }
        composable(Routes.ApplyPart.route) { ApplyPartScreen(navController) }
        composable(Routes.SupplyPart.route) {
            val service = navController.previousBackStackEntry?.savedStateHandle?.get<ServiceDTO>("service")
            if(service != null) {
                SupplyPartScreen(navController, service)
            }
        }
        composable(Routes.RequestedList.route) { RequestedScreen(navController) }
    }
}
