package com.app.socialmedia.domain.use_case.post

import android.util.Log
import com.app.socialmedia.domain.model.CreatePostModel
import com.app.socialmedia.domain.repository.PostRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(createPostModel: CreatePostModel){
        try {
            val response = postRepository.createPost(createPostModel)
            if(response.isSuccessful && response.body() != null) {
                Log.d("Create post response", "${response.body()}")
            }else{
                Log.e("Create post error body","${response.errorBody()}")
            }
        }catch(e:HttpException){
            Log.d("Create post Http Exception", "$e.localizedMessage")
        }catch(e: IOException){
            Log.e("Create post IO Exception", "$e.localizedMessage")
        }catch(e:Exception){
            Log.e("Create post Exception", "$e.localizedMessage")
        }

    }
}