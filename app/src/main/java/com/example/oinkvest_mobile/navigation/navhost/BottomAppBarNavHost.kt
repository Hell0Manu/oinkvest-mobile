package com.example.oinkvest_mobile.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oinkvest_mobile.main.dashboard.DashboardScreen
import com.example.oinkvest_mobile.main.history.HistoryScreen
import com.example.oinkvest_mobile.main.notification.NotificationScreen
import com.example.oinkvest_mobile.main.settings.SettingsScreen
import com.example.oinkvest_mobile.main.wallet.WalletScreen


import kotlinx.serialization.Serializable

@Composable
fun BottomAppBarNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = DashboardScreenRoute,
    ) {
        composable<DashboardScreenRoute> {
            DashboardScreen()
        }
        composable<WalletScreenRoute> {
            WalletScreen()
        }
        composable<NotificationScreenRoute> {
            NotificationScreen()
        }
        composable<HistoryScreenRoute> {
            HistoryScreen()
        }
       composable<SettingsScreenRoute> {
           SettingsScreen()
       }
    }
}

@Serializable
object DashboardScreenRoute

@Serializable
object WalletScreenRoute

@Serializable
object NotificationScreenRoute

@Serializable
object HistoryScreenRoute

@Serializable
object  SettingsScreenRoute