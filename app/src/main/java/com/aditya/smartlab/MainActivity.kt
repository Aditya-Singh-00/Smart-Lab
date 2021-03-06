package com.aditya.smartlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.aditya.smartlab.ui.navigation.Navigation
import com.aditya.smartlab.ui.theme.SmartLabTheme
import com.aditya.smartlab.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartLabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val mainViewModel: MainViewModel = hiltViewModel()
                    mainViewModel.userLoggedIn.value?.let { loggedIn ->
                        if (loggedIn) {
                            Navigation(
                                navController = navController,
                                startDestination = Screen.HomeScreen.route
                            )
                        } else {
                            Navigation(
                                navController = navController,
                                startDestination = Screen.LoginScreen.route
                            )
                        }
                    }
                }
            }
        }
    }
}
