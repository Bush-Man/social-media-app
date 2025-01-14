package com.app.socialmedia.data.repository

import com.app.socialmedia.data.dto.toUserRegisterDto
import com.app.socialmedia.data.remote.SocialMediaApi
import com.app.socialmedia.domain.model.UserRegister
import com.app.socialmedia.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: SocialMediaApi
):AuthRepository {
    override suspend fun register(userRegisterInfo: UserRegister): Response<Any> {
        println(userRegisterInfo.toUserRegisterDto())
        return authApi.register(userRegisterInfo.toUserRegisterDto())
    }

    override suspend fun getMe(): Response<Any> {
        return authApi.getMe()
    }
}