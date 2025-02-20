package com.dindaka.workshops_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.dindaka.workshops_android.presentation.navigation.AppNavigation
import com.dindaka.workshops_android.presentation.navigation.Routes
import com.dindaka.workshops_android.presentation.theme.Workshops_androidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainActivityViewModel = hiltViewModel()
            Workshops_androidTheme {
                val navigationController = rememberNavController()
                val isLoggedIn: Boolean by viewModel.isLogged.observeAsState(false)
                LaunchedEffect(isLoggedIn) {
                    if (isLoggedIn) {
                        navigationController.navigate(Routes.Home.route)
                    } else {
                        navigationController.navigate(Routes.Login.route)
                    }
                }
                AppNavigation(navigationController)
            }
        }
    }
}