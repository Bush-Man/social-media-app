package com.app.socialmedia.domain.use_case.post

import android.util.Log
import com.app.socialmedia.common.Resource
import com.app.socialmedia.data.dto.toPostModel
import com.app.socialmedia.domain.model.PostModel
import com.app.socialmedia.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LikePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: String):Flow<Resource<PostModel>> = flow {

        try {
        emit(Resource.Loading(isLoading = true)) // Indicate loading state

            val response = postRepository.likeUnlikePost(postId.toInt())
            emit(Resource.Loading(isLoading = false)) // Indicate loading state

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val likedPost = body.toPostModel()
                    Log.d("like post use case","$likedPost")

                    emit(Resource.Success(data =likedPost ))
                } else {
                    Log.d("like post","response body is null")
                    emit(Resource.Error(message = "Response body is null"))
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Log.e("like post",errorMessage)
                emit(Resource.Error(message = "Error: $errorMessage"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = "HTTP error: ${e.message}"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Unexpected error: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)
}

