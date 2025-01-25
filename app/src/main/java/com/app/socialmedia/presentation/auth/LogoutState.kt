package com.app.socialmedia.presentation.auth

data class LogoutState(
    val message: String = "",
    val error:String= "",
    val isLoading:Boolean = false
)
