package com.app.socialmedia.presentation.auth

sealed class RegisterScreenEvents {
    data class onNameChange(val name:String):RegisterScreenEvents()
    data class onPasswordChange(val password:String):RegisterScreenEvents()
    data class onPasswordConfirmChange(val passwordConfirm:String):RegisterScreenEvents()
    data class onEmailChange(val email:String):RegisterScreenEvents()
    data object onRegisterButtonClick : RegisterScreenEvents()


}