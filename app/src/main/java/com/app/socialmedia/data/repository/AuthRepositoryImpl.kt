package com.app.socialmedia.data.repository

import com.app.socialmedia.data.dto.LoginResponse
import com.app.socialmedia.data.dto.toUserRegisterDto
import com.app.socialmedia.data.remote.SocialMediaApi
import com.app.socialmedia.data.remote.api_responses.CommonRes
import com.app.socialmedia.data.remote.api_responses.RegistrationApiResponse
import com.app.socialmedia.domain.model.UserLogin
import com.app.socialmedia.domain.model.UserRegister
import com.app.socialmedia.domain.model.toUserLoginDto
import com.app.socialmedia.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: SocialMediaApi
):AuthRepository {
    override suspend fun register(userRegisterInfo: UserRegister): Response<RegistrationApiResponse> {
        return authApi.register(userRegisterInfo.toUserRegisterDto())
    }

    override suspend fun login(userLoginInfo: UserLogin): Response<LoginResponse> {
        return authApi.login(userLoginInfo.toUserLoginDto())
    }
    override suspend fun logout(): Response<CommonRes> {
        return authApi.logout()
    }



}