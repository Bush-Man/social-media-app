package com.app.socialmedia.data.remote.api_responses

import com.google.gson.annotations.SerializedName

sealed class RegistrationApiResponse{
    data class Success(
        @SerializedName("message") val message:String
    ):RegistrationApiResponse()

    data class ValidationError(
        @SerializedName("errors") val errors:Map<String,List<String>>,
        @SerializedName("message") val message:String
    ):RegistrationApiResponse()
    data class ServeError(
        @SerializedName("message")val message: String,
        @SerializedName("error") val error:String
    ):RegistrationApiResponse()
}
