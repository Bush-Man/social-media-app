package com.app.socialmedia.data.dto

import com.google.gson.annotations.SerializedName

data class LoginDto (
    val email:String,
    val password:String
)

data class LoginResponse(
    val token: String,
    val user: UserDto
)
