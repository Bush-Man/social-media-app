package com.app.socialmedia.domain.model

sealed class RegistrationUIResponse<out T>{

    data class Success<out T>(val data:T):RegistrationUIResponse<T>()

    data class ValidationError(
        val message:String,
        val errorData:Map<String,List<String>>
    ):RegistrationUIResponse<Nothing>()

    data class ServerError(
        val message: String,
        val errorData: Map<String, List<String>>
    ) : RegistrationUIResponse<Nothing>()

    data class NetworkError(val message: String) : RegistrationUIResponse<Nothing>()

    data class UnexpectedError(val message: String) : RegistrationUIResponse<Nothing>()

    data object Loading : RegistrationUIResponse<Nothing>()


}




