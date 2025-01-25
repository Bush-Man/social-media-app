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

class GetUserPostsUsecase @Inject constructor(
    val postRepository: PostRepository
) {
    suspend operator fun invoke(userId:String): Flow<Resource<List<PostModel>>> = flow {
        try {
            emit(Resource.Loading(isLoading = true))
            Log.d("user id",userId)
            val response = postRepository.getUserPosts(userId.toInt())
            Log.d("get user posts use case response","${response.body()}")

            if (response.isSuccessful && response.body() != null ) {

                val postsData = response.body()
                Log.d("get user posts use case response posts data", postsData.toString())

                if(!postsData.isNullOrEmpty()){
                    val posts = postsData.map{it.toPostModel()}
                    Log.d("get user posts use case response posts ", posts.toString())

                    emit(Resource.Success(posts))
                }
            } else {
                Log.d("Get All PostsFlow Failed","$response")

                emit(Resource.Error(response.message() ?: "Unknown error occurred fetching posts"))
            }

        } catch (e: HttpException) {
            e.localizedMessage?.let { Log.d("Get user PostsFlow Failed", it) }

            emit(Resource.Error("Server error: ${e.localizedMessage}"))
        } catch (e: IOException) {
            Log.d("Get user PostsFlow Failed","Failed to get posts")

            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)
}