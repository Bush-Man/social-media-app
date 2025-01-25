package com.app.socialmedia.presentation.chat

import com.app.socialmedia.domain.model.MessageModel

data class ChatRoomUIResponse(
    val messages:List<MessageModel> = emptyList(),
    val error:String = ""
)
