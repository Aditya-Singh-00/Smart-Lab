package com.aditya.smartlab.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = BlueLight,
    primaryVariant = BlueDark,
    secondary = BlueLight,
    secondaryVariant = BlueDark,
    background = WhiteGray,
    surface = White,
    onPrimary = White,
    onBackground = Black,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = BlueLight,
    primaryVariant = BlueDark,
    secondary = BlueLight,
    secondaryVariant = BlueDark,
    background = WhiteGray,
    surface = White,
    onPrimary = White,
    onBackground = Black,
)

@Composable
fun SmartLabTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}