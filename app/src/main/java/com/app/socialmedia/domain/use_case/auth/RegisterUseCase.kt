package com.app.socialmedia.domain.use_case.auth

import android.util.Log
import com.app.socialmedia.common.Resource
import com.app.socialmedia.domain.model.UserRegister
import com.app.socialmedia.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(userRegisterInfo: UserRegister):Flow<Resource<Any>> = flow{
        try {
            emit(Resource.Loading(isLoading = true))
            val response = authRepository.register(userRegisterInfo)
            Log.d("RegisterFlow","Response: $response")
            emit(Resource.Loading(isLoading = false))

            emit(Resource.Success(response))

        }catch(e: HttpException){
            Log.e("RegisterFlow", "HttpException: ${e.localizedMessage}", e)
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occured"))

        }catch (e:IOException){
            Log.e("RegisterFlow", "IOException: ${e.message}", e)
            emit(Resource.Error(message =  "check internet connection"))

        }
}
}