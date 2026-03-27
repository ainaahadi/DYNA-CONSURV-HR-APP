package com.example.hrapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hrapp.presentation.screens.auth.LoginScreen
import com.example.hrapp.presentation.screens.main.MainScreen
import com.example.hrapp.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()
        
        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable("main") {
                MainScreen()
            }
        }
    }
}
