package com.example.oinkvest_mobile.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oinkvest_mobile.main.feed.FeedScreen
import com.example.oinkvest_mobile.main.friends.FriendsScreen
import com.example.oinkvest_mobile.main.profile.ProfileScreen
import com.example.oinkvest_mobile.main.search.SearchScreen

import kotlinx.serialization.Serializable

@Composable
fun BottomAppBarNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = FeedScreenRoute,
    ) {
        composable<FeedScreenRoute> {
            FeedScreen()
        }
        composable<SearchScreenRoute> {
            SearchScreen()
        }
        composable<FriendsScreenRoute> {
           FriendsScreen()
        }
        composable<ProfileScreenRoute> {
            ProfileScreen()
        }
    }
}

@Serializable
object FeedScreenRoute

@Serializable
object SearchScreenRoute

@Serializable
object FriendsScreenRoute

@Serializable
object ProfileScreenRoute