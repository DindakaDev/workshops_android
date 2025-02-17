package com.dindaka.workshops_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.dindaka.workshops_android.presentation.navigation.AppNavigation
import com.dindaka.workshops_android.presentation.theme.Workshops_androidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Workshops_androidTheme {
                val navigationController = rememberNavController()
                AppNavigation(navigationController)
            }
        }
    }
}