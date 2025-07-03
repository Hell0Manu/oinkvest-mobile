package com.example.oinkvest_mobile.main.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.oinkvest_mobile.navigation.item.BottomNavItem
import com.example.oinkvest_mobile.navigation.navhost.BottomAppBarNavHost
import com.example.oinkvest_mobile.navigation.navhost.DashboardScreenRoute
import com.example.oinkvest_mobile.navigation.navhost.HistoryScreenRoute
import com.example.oinkvest_mobile.navigation.navhost.NotificationScreenRoute
import com.example.oinkvest_mobile.navigation.navhost.SettingsScreenRoute
import com.example.oinkvest_mobile.navigation.navhost.WalletScreenRoute
import com.example.oinkvest_mobile.R
import com.example.oinkvest_mobile.presentation.viewmodel.HomeViewModel
import com.example.oinkvest_mobile.presentation.components.BiometricSettingsDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {

    val bottomNavController = rememberNavController()

    var selectedItem by remember {
        mutableIntStateOf(0)
    }
    val context = LocalContext.current
    val showDialog by viewModel.showDialog.collectAsState()
    val isBiometricEnabled by viewModel.isBiometricEnabled.collectAsState()

    Scaffold (
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.top_bar_icon),
                            contentDescription = "Top Bar Icon",
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .size(100.dp)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.fingerprint),
                            contentDescription = "Fingerprint Icon",
                            modifier = Modifier
                                .absolutePadding(right = 16.dp)
                                .size(25.dp)
                                .clickable(indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        viewModel.onFingerprintIconClicked(context)
                                    }),
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF040313),
                    titleContentColor = Color.White
                )


            )


            if (showDialog) {
                BiometricSettingsDialog(
                    isCurrentlyEnabled = isBiometricEnabled,
                    onConfirm = { viewModel.confirmBiometricChange(context) },
                    onDismiss = { viewModel.dismissDialog() }
                )
            }

        },

        bottomBar = {
            NavigationBar(
                modifier = Modifier,
                containerColor = Color(0xFF040313),
                content = {
                    BottomNavItem.items.forEachIndexed { index, item ->
                        if (index == 0){
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        AddBottomItem(
                            item = item,
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                when(index) {
                                    0 -> bottomNavController.navigate(DashboardScreenRoute)
                                    1 -> bottomNavController.navigate(NotificationScreenRoute)
                                    2 -> bottomNavController.navigate(WalletScreenRoute)
                                    3 -> bottomNavController.navigate(HistoryScreenRoute)
                                    4 -> bottomNavController.navigate(SettingsScreenRoute)
                                }
                            },
//                            label = {
//                                if(selectedItem == index) {
//                                    Text(
//                                        text = stringResource(id = item.title),
//                                        style = TextStyle(
//                                            color = Color.White,
//                                            fontSize = 12.sp,
//                                            fontWeight = if (selectedItem == index) {
//                                                androidx.compose.ui.text.font.FontWeight.Bold
//                                            } else {
//                                                androidx.compose.ui.text.font.FontWeight.Normal
//                                            }
//                                        )
//
//                                    )
//                                }
//                            }
                        )

                        if (index == BottomNavItem.items.size - 1){
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(Color(0xFF040313))

            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.background),
//                    contentDescription = null,
//                    modifier = Modifier.matchParentSize(),
//                    contentScale = ContentScale.Crop
//                )
                BottomAppBarNavHost(bottomNavController)
            }

        }
    )
}

@Composable
fun RowScope.AddBottomItem(
    item: BottomNavItem,
    selected: Boolean,
    label: @Composable (() -> Unit) ? = null,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        label = label,
        icon = {
            Icon(
                painter = painterResource(id = if (selected) item.selectedIcon else item.unSelectedIcon),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 5.dp)
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            unselectedIconColor = Color.Gray,
            indicatorColor = Color.Transparent

        )
    )

}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}