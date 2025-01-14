package com.app.socialmedia.data.dto

import com.app.socialmedia.domain.model.UserRegister
import com.google.gson.annotations.SerializedName

data class UserRegisterDto(

    @SerializedName("name")
    val name:String,
    @SerializedName("email")
    val email:String,
    @SerializedName("password")
    val password:String,
    @SerializedName("password_confirmation")
    val passwordConfirmation:String
)
fun UserRegisterDto.toUserRegister():UserRegister{
    return UserRegister(
        name,
        email,
        password,
        passwordConfirmation
    )
}

fun UserRegister.toUserRegisterDto():UserRegisterDto{

        return UserRegisterDto(
            name = name,
            email = email,
            password = password,
            passwordConfirmation = passwordConfirmation

    )
}