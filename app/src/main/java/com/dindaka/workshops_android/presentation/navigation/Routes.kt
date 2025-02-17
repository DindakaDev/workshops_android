package com.dindaka.workshops_android.presentation.navigation

sealed class Routes(val route: String) {
    data object Login : Routes("login")
    data object Home : Routes("home")
    data object SupplyPart : Routes("supply_part")
    data object ApplyPart: Routes("apply_part")
    data object RequestedList: Routes("requested_list")
}