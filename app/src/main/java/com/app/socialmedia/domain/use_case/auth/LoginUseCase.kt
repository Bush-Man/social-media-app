package com.app.socialmedia.domain.use_case.auth

import android.util.Log
import com.app.socialmedia.common.Resource
import com.app.socialmedia.data.dto.LoginResponse
import com.app.socialmedia.domain.model.UserLogin
import com.app.socialmedia.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(userLoginInfo: UserLogin): Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading(isLoading = true))
            Log.d("user detaila","$userLoginInfo")

            val response = authRepository.login(userLoginInfo)
            Log.d("RawResponse", "Code: ${response.code()}, Body: ${response.body()}, Error: ${response.errorBody()?.string()}")
            emit(Resource.Loading(isLoading = false))

            if (response.isSuccessful && response.body() != null) {
                Log.d("LoginFlow","Response: ${response.body()}")
                emit(Resource.Success(response.body()!!))

            } else {
                Log.d("LoginFlow", "Response else block: ${response.errorBody()}")

                emit(Resource.Error(response.message() ?: "Unknown error occurred"))
            }

        } catch (e: HttpException) {
            Log.d("LoginFlow","Response http e: ${e.localizedMessage}")

            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            Log.d("LoginFlow","Response io e: ${e.localizedMessage}")

            emit(Resource.Error("Please check your internet connection"))
        }catch(e:Exception){
            Log.d("LoginFlow","Response io exceptiom: ${e.localizedMessage}")

            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}
