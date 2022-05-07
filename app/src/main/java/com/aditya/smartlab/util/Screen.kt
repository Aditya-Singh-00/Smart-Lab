package com.aditya.smartlab.util

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object DeviceDetailScreen: Screen("device_detail_screen")
}