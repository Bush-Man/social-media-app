package com.app.socialmedia.presentation.auth

sealed class LoginScreenEvents {

    data class onPasswordChange(val password: String) : LoginScreenEvents()
    data class onEmailChange(val email: String) : LoginScreenEvents()
    data object onLoginButtonClick : LoginScreenEvents()


}
