package com.aditya.smartlab.ui.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun StandardTextField(
    text: String,
    modifier: Modifier = Modifier,
    hint: String = "",
    singleLine: Boolean = true,
    maxLines: Int = 1,
    maxLength: Int = 15,
    isError: Boolean = false,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = text,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface),
        placeholder = {
            Text(
                text = hint,
                color = MaterialTheme.colors.onBackground.copy(0.60f)
            )
        },
        leadingIcon = if (leadingIcon != null) {
            @Composable {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        } else {
            null
        },
        trailingIcon = if (keyboardType == KeyboardType.Password) {
            @Composable {
                val icon = if (passwordVisibility) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                IconButton(
                    onClick = { passwordVisibility = !passwordVisibility }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Password Visibility",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        } else {
            null
        },
        isError = isError,
        visualTransformation = if (passwordVisibility) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = singleLine,
        maxLines = maxLines,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onBackground
        )
    )
}
