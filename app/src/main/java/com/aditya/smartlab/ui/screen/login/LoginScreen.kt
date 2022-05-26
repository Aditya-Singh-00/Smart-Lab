package com.aditya.smartlab.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.smartlab.ui.screen.components.AlertDialogCard
import com.aditya.smartlab.ui.screen.components.StandardTextField

@Composable
fun LoginScreen(
    onSuccessfulLogin: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    loginViewModel.user.value?.let { onSuccessfulLogin() }

    val errorMessage = loginViewModel.errorMessage.value

    if(errorMessage.isNotEmpty()) {
        AlertDialogCard(
            title = "Error",
            text = errorMessage,
            confirmButtonText = "OK",
            onConfirmClick = { loginViewModel.resetErrorMessage() },
            onDialogDismiss = { loginViewModel.resetErrorMessage() }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Login",
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            StandardTextField(
                text = loginViewModel.emailText.value,
                onValueChange = { loginViewModel.setEmailText(it) },
                hint = "email",
                maxLength = 30,
                leadingIcon = Icons.Filled.Email,
                keyboardType = KeyboardType.Email,
                isError = loginViewModel.emailTextError.value
            )
            Spacer(modifier = Modifier.height(12.dp))
            StandardTextField(
                text = loginViewModel.passwordText.value,
                onValueChange = { loginViewModel.setPasswordText(it) },
                hint = "password",
                maxLength = 30,
                leadingIcon = Icons.Filled.Lock,
                keyboardType = KeyboardType.Password,
                isError = loginViewModel.passwordTextError.value
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    loginViewModel.loginUser()
                },
                modifier = Modifier
                    .align(Alignment.End),
                enabled = !loginViewModel.showProgressBar.value
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
        if (loginViewModel.showProgressBar.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(32.dp)
            )
        }
    }
}
