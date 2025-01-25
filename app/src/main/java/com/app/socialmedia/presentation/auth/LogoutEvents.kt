package com.app.socialmedia.presentation.auth

sealed class LogoutEvents{
    data object LogoutConfirmEvent:LogoutEvents()

}
