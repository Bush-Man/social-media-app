package com.app.socialmedia.domain.use_case.auth

import android.util.Log
import com.app.socialmedia.common.Resource
import com.app.socialmedia.data.remote.api_responses.RegistrationApiResponse
import com.app.socialmedia.domain.model.RegistrationUIResponse
import com.app.socialmedia.domain.model.UserRegister
import com.app.socialmedia.domain.repository.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.util.HashMap
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,

) {
    operator fun invoke(userRegisterInfo: UserRegister): Flow<Resource<RegistrationUIResponse<*>>> = flow {
        val gson = Gson()
        try {
            emit(Resource.Loading(isLoading = true))

            // Make the API call
            val response = authRepository.register(userRegisterInfo)
            val errorBody = response.errorBody()?.string()
            val dataBody = response.body()
            emit(Resource.Loading(isLoading = false))

            when{
                response.isSuccessful && dataBody   is RegistrationApiResponse.Success ->{

//                    Log.d("RegisterBody", "Response: $dataBody")
                    emit(Resource.Success(RegistrationUIResponse.Success(dataBody)))

                }
                response.code() == 422 ->{
                    val validationError = gson.fromJson(errorBody, RegistrationApiResponse.ValidationError::class.java)
                    val errorData  = HashMap<String,List<String>>()
                     validationError.errors.entries.forEach { entry ->
                            errorData[entry.key] = entry.value
                  Log.d("ValidationError", "Response: ${entry.key} + ${entry.value}")
                    }

                    emit(Resource.Error(message = validationError.message, errorData = errorData))

                }
                response.code() == 500 ->{
                    val serverError = gson.fromJson(errorBody, RegistrationApiResponse.ServeError::class.java)
                    val errorData  = HashMap<String,List<String>>()

                        errorData["server"] = listOf(serverError.error)

                    Log.d("Register ServerError", "Response: $serverError")
                    emit(Resource.Error(message = serverError.message, errorData = errorData))
                }
            }




        } catch (e: HttpException) {
//            Log.e("RegisterFlow", "HttpException: ${e.localizedMessage}", e)
            emit(Resource.Error(message = "hello", errorData = null))
        } catch (e: IOException) {
            //Hanlde  timeout exception
//            Log.e("RegisterFlow", "IOException: ${e.message}", e)
            emit(Resource.Error(message = "Check your internet connection", errorData =null))
        } catch (e: Exception) {
//            Log.e("RegisterFlow", "Unexpected exception: ${e.localizedMessage}", e)
            emit(Resource.Error(message = "An unexpected error occurred", errorData =null))
        }
    }.flowOn(Dispatchers.IO)
}
