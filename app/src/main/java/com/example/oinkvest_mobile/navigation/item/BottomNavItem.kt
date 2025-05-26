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
    data object Feed : BottomNavItem(
        title = R.string.bottom_bar_item_feed,
        selectedIcon = R.drawable.home_fill,
        unSelectedIcon = R.drawable.home_line
    )

    data object Search : BottomNavItem(
        title = R.string.bottom_bar_item_search,
        selectedIcon = R.drawable.pie_chart_fill,
        unSelectedIcon = R.drawable.pie_chart_line

    )

    data object  Friends: BottomNavItem(
        title = R.string.bottom_bar_item_friends,
        selectedIcon = R.drawable.settings_fill,
        unSelectedIcon = R.drawable.settings_line
    )

    data object  Profile : BottomNavItem(
        title = R.string.bottom_bar_item_profile,
        selectedIcon = R.drawable.user_fill,
        unSelectedIcon = R.drawable.user_line
    )

    companion object {
        val items = listOf(Feed, Search, Friends, Profile)
    }
}