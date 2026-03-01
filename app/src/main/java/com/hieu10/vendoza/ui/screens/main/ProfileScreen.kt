package com.hieu10.vendoza.ui.screens.main

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    Text(text = "Profile screen")
    Button(onClick = onLogout) {
        Text("Logout")
    }
}