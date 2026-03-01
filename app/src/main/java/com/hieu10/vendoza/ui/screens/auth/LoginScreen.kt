package com.hieu10.vendoza.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hieu10.vendoza.ui.navigation.destinations.AuthDestinations

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    Column {
        Text(text = "Login Screen")
        Button(onClick = { navController.navigate(AuthDestinations.Register) }) {
            Text("Go to Register")
        }
        Button(onClick = { onLoginSuccess() }) {
            Text("Login (Demo)")
        }
    }
}