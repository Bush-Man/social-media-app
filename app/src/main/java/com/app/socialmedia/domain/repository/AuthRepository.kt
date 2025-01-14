package com.app.socialmedia.domain.repository

import com.app.socialmedia.common.Resource
import com.app.socialmedia.domain.model.UserRegister
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AuthRepository {
    suspend fun register(userRegisterInfo: UserRegister):Response<Any>

    suspend fun getMe():Response<Any>
}