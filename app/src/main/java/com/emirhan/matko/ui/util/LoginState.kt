package com.emirhan.matko.ui.util

sealed class LoginState {
    object  Idle: LoginState()
    object Loading: LoginState()
    object Success: LoginState()
    data class Error(val message: String): LoginState()
}