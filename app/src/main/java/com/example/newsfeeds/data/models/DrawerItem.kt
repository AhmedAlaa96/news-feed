package com.example.newsfeeds.data.models

import androidx.annotation.DrawableRes


data class DrawerItem(val itemTitle: String, @DrawableRes val itemIcon: Int, var isSelected: Boolean)