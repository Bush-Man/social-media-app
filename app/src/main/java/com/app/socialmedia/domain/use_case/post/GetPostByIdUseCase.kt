package com.app.socialmedia.domain.use_case.post

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

class GetPostByIdUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(postId:String):Flow<Resource<PostModel>> = flow {
        try{
            val response = postRepository.getPostById(postId.toInt())
            if(response.isSuccessful && response.body() != null){
                val post = response.body()
                if(post != null){
                    emit(Resource.Success(data = post.toPostModel()))

                }

            }else{
                emit(Resource.Error(message= response.errorBody().toString()))
            }
        }catch(e: HttpException){
          emit(Resource.Error(message = e.localizedMessage))
        }catch(e:IOException){
       emit(Resource.Error(message = e.localizedMessage))
        }catch(e:Exception){
            emit(Resource.Error(message = e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}