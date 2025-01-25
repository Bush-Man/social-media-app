package com.app.socialmedia.domain.repository

import com.app.socialmedia.data.dto.MessageDto
import retrofit2.Response

interface ChatRepository {

    suspend fun getChatRoomMessages(chatRoomId:String):Response<List<MessageDto>>
}