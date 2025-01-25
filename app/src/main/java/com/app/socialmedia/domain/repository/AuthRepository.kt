package com.app.socialmedia.domain.repository

import com.app.socialmedia.data.dto.LoginResponse
import com.app.socialmedia.data.remote.api_responses.CommonRes
import com.app.socialmedia.data.remote.api_responses.RegistrationApiResponse
import com.app.socialmedia.domain.model.UserLogin
import com.app.socialmedia.domain.model.UserRegister
import retrofit2.Response

interface AuthRepository {
    suspend fun register(userRegisterInfo: UserRegister):Response<RegistrationApiResponse>

    suspend fun login(userLoginInfo:UserLogin):Response<LoginResponse>

    suspend fun logout(): Response<CommonRes>
}