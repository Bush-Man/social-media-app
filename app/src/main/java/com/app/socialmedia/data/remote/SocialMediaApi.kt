package com.app.socialmedia.data.remote

import com.app.socialmedia.common.Constants
import com.app.socialmedia.data.dto.UserRegisterDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SocialMediaApi {

@POST("register")
suspend fun register(@Body registerDto: UserRegisterDto):Response<Any>

@GET("me")
suspend fun getMe():Response<Any>
}