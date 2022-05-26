package com.aditya.smartlab.ui.screen.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp

@Composable
fun AlertDialogCard(
    title: String,
    text: String,
    confirmButtonText: String,
    dismissButtonText: String = "",
    onConfirmClick: () -> Unit = { },
    onDialogDismiss: () -> Unit = { }
) {
    val openDialog = remember {
        mutableStateOf(true)
    }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { onDialogDismiss() },
            title = {
                Text(
                    text = title,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = text,
                    fontSize = 16.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { onConfirmClick() }
                ) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onDialogDismiss() }
                ) {
                    Text(text = dismissButtonText)
                }
            },
            backgroundColor = MaterialTheme.colors.background
        )
    }
}