package com.hieu10.vendoza.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun RegisterScreen(
    navController: NavController,
    onBackToLogin: () -> Unit
) {
    Column {
        Text(text = "Register Screen")
        Button(onClick = onBackToLogin) {
            Text(text = "Back to Login")
        }
    }
}