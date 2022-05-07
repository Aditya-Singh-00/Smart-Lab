package com.aditya.smartlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.aditya.smartlab.ui.navigation.Navigation
import com.aditya.smartlab.ui.theme.DarkGray
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
                    color = DarkGray
                ) {
                    val navController = rememberNavController()
                    Navigation(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    )
                }
            }
        }
    }
}
