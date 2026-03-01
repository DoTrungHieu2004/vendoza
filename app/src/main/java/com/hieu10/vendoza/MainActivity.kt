package com.hieu10.vendoza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hieu10.vendoza.ui.navigation.RootNavGraph
import com.hieu10.vendoza.ui.theme.VendozaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VendozaTheme {
                RootNavGraph()
            }
        }
    }
}