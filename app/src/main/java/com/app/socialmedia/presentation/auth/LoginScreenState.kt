package com.app.socialmedia.presentation.auth

import com.app.socialmedia.data.dto.LoginResponse

data class LoginScreenState (
  val email:String ="",
    val password:String ="",
    val isLoading:Boolean = false,
    val error:String? = null,
    val response: LoginResponse? = null,


)
