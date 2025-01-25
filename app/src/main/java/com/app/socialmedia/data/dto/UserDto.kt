package com.app.socialmedia.data.dto

import com.app.socialmedia.domain.model.UserModel
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("email_verified_at") val emailVerifiedAt: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("remember_token")val rememberToken: String?,
    @SerializedName("password") val password: String?,
)
fun UserDto.toUserModel():UserModel{
    return UserModel(
        id = id,
    name = name,
    email = email,
    emailVerifiedAt = emailVerifiedAt,
    createdAt = createdAt,
    updatedAt = updatedAt,
    rememberToken = rememberToken,
    password = password
    )
}

fun UserModel.toUserDto():UserDto{
    return UserDto(
        id = id,
        name = name,
        email = email,
        emailVerifiedAt = emailVerifiedAt,
        createdAt = createdAt,
        updatedAt = updatedAt,
        rememberToken = rememberToken,
        password = password
    )
}