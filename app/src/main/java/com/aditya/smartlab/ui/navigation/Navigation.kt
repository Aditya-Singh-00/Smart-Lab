package com.aditya.smartlab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aditya.smartlab.ui.screen.device_detail.DeviceDetailScreen
import com.aditya.smartlab.ui.screen.home.HomeScreen
import com.aditya.smartlab.ui.screen.login.LoginScreen
import com.aditya.smartlab.util.Screen

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onSuccessfulLogin = {
                    navController.popBackStack()
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                navigateTo = {
                    navController.navigate(Screen.DeviceDetailScreen.route + "/$it")
                }
            )
        }
        composable(
            route = Screen.DeviceDetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            DeviceDetailScreen()
        }
    }
}