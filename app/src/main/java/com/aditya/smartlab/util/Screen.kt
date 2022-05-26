package com.aditya.smartlab.util

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object HomeScreen: Screen("home_screen")
    object DeviceDetailScreen: Screen("device_detail_screen")
}