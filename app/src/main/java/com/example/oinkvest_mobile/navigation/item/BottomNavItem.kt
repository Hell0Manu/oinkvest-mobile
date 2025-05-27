package com.example.oinkvest_mobile.navigation.item

import androidx.annotation.DrawableRes
import com.example.oinkvest_mobile.R

/*
* Titulo do icone
* Icone selecionado
* Icone não selecionado
*/
sealed class BottomNavItem (
    val title: Int,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int
) {
    data object Dashboard : BottomNavItem(
        title = R.string.bottom_bar_item_dashboard,
        selectedIcon = R.drawable.pie_chart_fill,
        unSelectedIcon = R.drawable.pie_chart_line
    )

    data object  Wallet: BottomNavItem(
        title = R.string.bottom_bar_item_wallet,
        selectedIcon = R.drawable.wallet_fill,
        unSelectedIcon = R.drawable.wallet_line
    )

    data object Notification : BottomNavItem(
        title = R.string.bottom_bar_item_notification,
        selectedIcon = R.drawable.notification_fill,
        unSelectedIcon = R.drawable.notification_line
    )

    data object  History : BottomNavItem(
        title = R.string.bottom_bar_item_history,
        selectedIcon = R.drawable.history_fill,
        unSelectedIcon = R.drawable.history_line
    )

    data object  Settings : BottomNavItem(
        title = R.string.bottom_bar_item_settings,
        selectedIcon = R.drawable.settings_fill,
        unSelectedIcon = R.drawable.settings_line
    )

    companion object {
        val items = listOf(Dashboard, Notification, Wallet, History, Settings)
    }
}