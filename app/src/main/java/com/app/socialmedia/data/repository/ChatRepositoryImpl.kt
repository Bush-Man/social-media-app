package com.app.socialmedia.data.repository

import com.app.socialmedia.data.dto.MessageDto
import com.app.socialmedia.data.remote.ChatApi
import com.app.socialmedia.domain.repository.ChatRepository
import retrofit2.Response
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
   private val chatApi: ChatApi
):ChatRepository{
    override suspend fun getChatRoomMessages(chatRoomId: String): Response<List<MessageDto>> {
        return chatApi.getChatRoomMessages(chatRoomId);
    }

}