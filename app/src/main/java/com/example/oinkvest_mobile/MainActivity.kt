package com.example.oinkvest_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.oinkvest_mobile.navigation.navhost.MyNavHost
import com.example.oinkvest_mobile.ui.theme.OinkvestmobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OinkvestmobileTheme {
                MyNavHost(navHostController = rememberNavController())
            }
        }
    }
}
