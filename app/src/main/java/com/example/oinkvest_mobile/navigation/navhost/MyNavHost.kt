package com.example.oinkvest_mobile.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oinkvest_mobile.data.local.AppPreferences
import com.example.oinkvest_mobile.main.home.HomeScreen
import com.example.oinkvest_mobile.main.login.LoginScreen
import com.example.oinkvest_mobile.main.register.RegisterScreen
import com.example.oinkvest_mobile.presentation.onboarding.OnboardingScreen

@Composable
fun MyNavHost(navHostController: NavHostController) {
    val context = LocalContext.current

    val startDestination = if (AppPreferences.isOnboardingCompleted(context)) {
        "login" // Se o onboarding foi concluído, comece pelo login
    } else {
        "onboarding" // Se for a primeira vez, comece pelo onboarding
    }

    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable("onboarding") {
            OnboardingScreen(navHostController)
        }
        composable("login") {
            LoginScreen(navHostController)
        }
        composable("register") {
            RegisterScreen(navHostController)
        }
        composable("home") {
            HomeScreen(navHostController)
        }
    }
}
