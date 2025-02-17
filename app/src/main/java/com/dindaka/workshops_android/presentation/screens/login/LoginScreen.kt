package com.dindaka.workshops_android.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dindaka.workshops_android.R
import com.dindaka.workshops_android.presentation.components.NormalInput
import com.dindaka.workshops_android.presentation.components.PasswordInput
import com.dindaka.workshops_android.presentation.components.PrimaryButton
import com.dindaka.workshops_android.presentation.components.TitleTextComponent
import com.dindaka.workshops_android.presentation.navigation.Routes

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val navigateToHome by viewModel.navigateToHome.observeAsState(false)
    if (navigateToHome) {
        navController.navigate(Routes.Home.route) {
            popUpTo(Routes.Login.route) { inclusive = true }
        }
        viewModel.onNavigated()
    }
    Scaffold(
        content = { padding ->
            Box {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.size(16.dp))
                    FormLogin(viewModel)
                }
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize().clickable {  }) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    )
}

@Composable
fun FormLogin(viewModel: LoginViewModel) {
    val username: String by viewModel.username.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val isLoginEnabled: Boolean by viewModel.isLoginEnabled.observeAsState(false)
    Column(
        Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        TitleTextComponent(stringResource(R.string.app_name))
        Spacer(Modifier.size(16.dp))
        NormalInput(
            username,
            placeHolder = R.string.username,
            leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = "") }) {
            viewModel.onLoginChange(it, password)
        }
        Spacer(Modifier.size(8.dp))
        PasswordInput(password, placeHolder = R.string.password) {
            viewModel.onLoginChange(username, it)
        }
        Spacer(Modifier.size(20.dp))
        PrimaryButton(text = stringResource(R.string.btn_login), enabled = isLoginEnabled) {
            viewModel.onLoginSelected()
        }
    }
}

