package com.app.socialmedia.domain.model

import com.app.socialmedia.data.dto.LoginDto

data class UserLogin (
    val email:String,
    val password:String
)
fun UserLogin.toUserLoginDto(): LoginDto {
    return LoginDto(
        email = email,
        password = password
    )

}