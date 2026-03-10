package com.hieu10.vendoza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.hieu10.vendoza.data.local.TokenManager
import com.hieu10.vendoza.data.remote.ApiClient
import com.hieu10.vendoza.data.repository.AuthRepository
import com.hieu10.vendoza.ui.navigation.NavGraph
import com.hieu10.vendoza.ui.theme.VendozaTheme
import com.hieu10.vendoza.viewmodel.factory.AuthVMFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenManager = TokenManager(applicationContext)

        // Get AuthService and AuthRepository
        val authService = ApiClient.authService
        val authRepository = AuthRepository(authService, tokenManager)
        val authVMFactory = AuthVMFactory(authRepository)

        setContent {
            VendozaTheme {
                val navController = rememberNavController()
                NavGraph(navController, authVMFactory)
            }
        }
    }
}