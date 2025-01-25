package com.app.socialmedia.domain.use_case.post

import android.util.Log
import com.app.socialmedia.common.Resource
import com.app.socialmedia.data.dto.CommentDto
import com.app.socialmedia.data.dto.toPostCommentModel
import com.app.socialmedia.data.dto.toPostModel
import com.app.socialmedia.domain.model.CreateCommentModel
import com.app.socialmedia.domain.model.CreatePostModel
import com.app.socialmedia.domain.model.PostCommentModel
import com.app.socialmedia.domain.model.PostModel
import com.app.socialmedia.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateCommentUsecase @Inject constructor(
    private val postRepository: PostRepository

) {

    suspend operator fun invoke(comment:CreateCommentModel,postId:String):Flow<Resource<PostModel>> = flow{
        try {
            val response = postRepository.commentOnPost(comment=comment, postId = postId.toInt())
            Log.d("comment post res" , response.body().toString())


            if(response.isSuccessful && response.body() != null) {


                val post = response.body()?.toPostModel()
                Log.d("comment post res" , post.toString())
                emit(Resource.Success(post))
            }else{

                emit(Resource.Error("Failed to create comment no response from server"))
            }
        }catch(e: HttpException){

            emit(Resource.Error(e.localizedMessage))
        }catch(e: IOException){

            emit(Resource.Error(e.localizedMessage))
        }catch(e:Exception){

            emit(Resource.Error(e.localizedMessage))
        }

    }.flowOn(Dispatchers.IO)
}


