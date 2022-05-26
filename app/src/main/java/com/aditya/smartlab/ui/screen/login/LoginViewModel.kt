package com.aditya.smartlab.ui.screen.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.smartlab.data.repository.SmartLabRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: SmartLabRepository
) : ViewModel() {

    private val _user: MutableState<FirebaseUser?> = mutableStateOf(null)
    val user: State<FirebaseUser?> = _user

    private val _emailText = mutableStateOf("")
    val emailText: State<String> = _emailText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _emailTextError = mutableStateOf(false)
    val emailTextError: State<Boolean> = _emailTextError

    private val _passwordTextError = mutableStateOf(false)
    val passwordTextError: State<Boolean> = _passwordTextError

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    private val _showProgressBar = mutableStateOf(false)
    val showProgressBar: State<Boolean> = _showProgressBar

    fun setEmailText(value: String) {
        _emailText.value = value
        _emailTextError.value = false
    }

    fun setPasswordText(value: String) {
        _passwordText.value = value
        _passwordTextError.value = false
    }

    fun loginUser() {
        when {
            emailText.value.isBlank() -> {
                _emailTextError.value = true
                return
            }
            passwordText.value.isBlank() -> {
                _passwordTextError.value = true
                return
            }
        }
        _showProgressBar.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.login(
                email = emailText.value,
                password = passwordText.value
            )
            if (user == null) {
                _errorMessage.value = "Something went wrong"
            } else {
                _user.value = user
            }
        }.invokeOnCompletion {
            _showProgressBar.value = false
        }
    }

    fun resetErrorMessage() {
        _errorMessage.value = ""
    }
}