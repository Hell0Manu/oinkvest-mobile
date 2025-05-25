package com.example.oinkvest_mobile.main.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.oinkvest_mobile.navigation.item.BottomNavItem
import com.example.oinkvest_mobile.navigation.navhost.BottomAppBarNavHost

@Composable
fun HomeScreen(navHostController: NavHostController = rememberNavController()) {
    var selectedItem by remember {
        mutableStateOf(0)
    }

    Scaffold (
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape),
                containerColor = Color.Black,
                content = {
                    BottomNavItem.items.forEachIndexed { index, item ->
                        AddBottomItem(
                            item = item,
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                BottomAppBarNavHost(navHostController)
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
                contentDescription = null
            )
        }
    )

}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}