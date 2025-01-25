package com.app.socialmedia.domain.use_case.post

import android.util.Log
import com.app.socialmedia.common.Resource
import com.app.socialmedia.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SharePostUsecase @Inject constructor(
    private val postRepository: PostRepository
){
    operator suspend fun invoke(postId:String):Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading(isLoading = true))
            val response = postRepository.sharePost(postId.toInt())
            if(response.isSuccessful && response.body() != null){
                Log.d("Share post usecase",response.body().toString())
                emit(Resource.Success(data= response.body().toString()))
            }else{
                emit(Resource.Error(response.errorBody().toString()))
            }

        }catch (e: HttpException) {
        emit(Resource.Error(message = "HTTP error: ${e.message}"))
    } catch (e: IOException) {
        emit(Resource.Error(message = "Network error: ${e.message}"))
    } catch (e: Exception) {
        emit(Resource.Error(message = "Unexpected error: ${e.message}"))
    }
    }.flowOn(Dispatchers.IO)
}