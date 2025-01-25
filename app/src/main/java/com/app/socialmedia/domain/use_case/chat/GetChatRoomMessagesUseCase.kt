package com.app.socialmedia.domain.use_case.chat

import android.util.Log
import com.app.socialmedia.common.Resource
import com.app.socialmedia.data.dto.toMessageModel
import com.app.socialmedia.domain.model.MessageModel
import com.app.socialmedia.domain.repository.ChatRepository
import com.app.socialmedia.presentation.chat.ChatRoomUIResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetChatRoomMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
       suspend operator fun invoke(chatRoomId:String):Flow<Resource<List<MessageModel>>> = flow{
         try {
             val response = chatRepository.getChatRoomMessages(chatRoomId)
             if(response.isSuccessful && response.body() != null){
                 Log.d("Get chat usecase","${response.body()}")
                 val messages = response.body()!!.map { it.toMessageModel() }
                 emit(Resource.Success(data = messages))
             }else{
                 Log.d("Get chat usecase","Failed")
                 emit(Resource.Error(message = "Failed to fetch chatroom"))

             }
         }catch(e:HttpException){
             emit(Resource.Error(message = e.localizedMessage))

             Log.d("Get chat usecase error http", e.localizedMessage)

         }catch(e:IOException){
             emit(Resource.Error(message = e.localizedMessage))

             Log.d("Get chat usecase error io", e.localizedMessage)
         }catch(e:Exception){
             emit(Resource.Error(message = e.localizedMessage))

             Log.d("Get chat usecase error exception", e.localizedMessage)
         }

}
}