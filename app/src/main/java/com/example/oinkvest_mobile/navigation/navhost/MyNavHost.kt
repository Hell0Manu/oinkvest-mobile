package com.example.oinkvest_mobile.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oinkvest_mobile.main.home.HomeScreen
import kotlinx.serialization.Serializable

//Faz a navegação entre as telas
@Composable
fun MyNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = HomeScreenRoute
    ) {
        composable<HomeScreenRoute> {
            HomeScreen()
        }
    }
}

@Serializable
object HomeScreenRoute