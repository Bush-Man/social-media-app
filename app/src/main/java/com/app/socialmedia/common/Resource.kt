package com.app.socialmedia.common

sealed class Resource<T>(val data: T? = null,val message:String?=null,val isLoading: Boolean?=false) {
    class Success<T>(data: T?):Resource<T>(data)
    class Loading<T>(isLoading: T?):Resource<T>(isLoading)
    class Error<T>(message: String?,data: T?=null):Resource<T>(data,message)

}