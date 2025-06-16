package com.example.oinkvest_mobile.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oinkvest_mobile.main.home.HomeScreen
import com.example.oinkvest_mobile.main.login.LoginScreen
import com.example.oinkvest_mobile.main.register.RegisterScreen
import kotlinx.serialization.Serializable

@Composable
fun MyNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navHostController)
        }
        composable("register") {
            RegisterScreen(navHostController)
        }
        composable("home") {
            HomeScreen()
        }
    }
}

@Serializable
object HomeScreenRoute