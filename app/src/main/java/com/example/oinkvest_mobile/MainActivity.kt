package com.example.oinkvest_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.example.oinkvest_mobile.main.login.LoginScreen
import com.example.oinkvest_mobile.navigation.navhost.MyNavHost
import com.example.oinkvest_mobile.ui.theme.OinkvestmobileTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            OinkvestmobileTheme {
                MyNavHost(navHostController = rememberNavController())
            }
        }
    }
}
