package com.app.socialmedia.domain.use_case.auth

import android.util.Log
import com.app.socialmedia.common.Resource
import com.app.socialmedia.data.remote.api_responses.CommonRes
import com.app.socialmedia.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(): Flow<Resource<CommonRes>> = flow {
        try {
            emit(Resource.Loading(isLoading = true))


            val response = authRepository.logout()
            Log.d("RawResponse", "Code: ${response.code()}, Body: ${response.body()}, Error: ${response.errorBody()?.string()}")
            emit(Resource.Loading(isLoading = false))

            if (response.isSuccessful && response.body() != null) {
                Log.d("LogoutFlow","Response: ${response.body()}")
                emit(Resource.Success(response.body()!!))

            } else {
                Log.d("LogoutFlow", "Response else block: ${response.errorBody()}")

                emit(Resource.Error(response.message() ?: "Unknown error occurred"))
            }

        } catch (e: HttpException) {
            Log.d("LogoutFlow","Response http e: ${e.localizedMessage}")

            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            Log.d("LogoutFlow","Response io e: ${e.localizedMessage}")

            emit(Resource.Error("Please check your internet connection"))
        }catch(e:Exception){
            Log.d("LogoutFlow","Response io exceptiom: ${e.localizedMessage}")

            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}
