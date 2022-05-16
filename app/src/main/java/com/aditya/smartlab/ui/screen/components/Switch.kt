package com.aditya.smartlab.ui.screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    size: Float = 40f,
    checkedThumbColor: Color = MaterialTheme.colors.secondaryVariant,
    checkedTrackColor: Color = checkedThumbColor.copy(alpha = 0.54f),
    uncheckedThumbColor: Color = MaterialTheme.colors.surface,
    uncheckedTrackColor: Color = MaterialTheme.colors.onSurface
) {

    val height = size.times(0.65f)
    val radius = height.times(0.40f)

    val thumbColor = animateColorAsState(
        if (checked) {
            checkedThumbColor
        } else {
            uncheckedThumbColor
        }
    )

    val trackColor = animateColorAsState(
        if (checked) {
            checkedTrackColor
        } else {
            uncheckedTrackColor
        }
    )

    val offsetX = animateFloatAsState(
        if (checked) {
            size - height / 2.0f
        } else {
            height / 2.0f
        }
    )

    Canvas(
        modifier = Modifier
            .width(size.dp)
            .height(height.dp)
            .background(
                color = trackColor.value,
                shape = RoundedCornerShape(50)
            )
            .clickable { onCheckedChange() }
    ) {
        drawCircle(
            color = thumbColor.value,
            radius = radius,
            center = Offset(
                x = offsetX.value,
                y = height / 2.0f)
        )
    }
}