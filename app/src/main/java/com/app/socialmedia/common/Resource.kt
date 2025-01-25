package com.app.socialmedia.common


sealed class Resource<out T> {
    data class Success<out T>(val data: T?=null) : Resource<T>()
    data class Error(val message: String?=null, val errorData: Map<String, List<String>>?=null) : Resource<Nothing>()
    data class Loading(val isLoading: Boolean=false) : Resource<Nothing>()
}




//sealed class Resource<T>(
//    val data: T? = null,
//    val message: String? = null,
//    val errorData:Map<String,List<String>>? = null
//) {
//    class Loading<T>(val isLoading: Boolean = false) : Resource<T>()
//    class Success<T>(data: T) : Resource<T>(data)
//    class Error<T>(message: String, errorData:Map<String,List<String>>? ) : Resource<T>(errorData =errorData, message=message)
//}
