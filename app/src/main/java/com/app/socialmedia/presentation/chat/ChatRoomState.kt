package com.app.socialmedia.presentation.chat

import com.app.socialmedia.domain.model.MessageModel

data class ChatRoomState(
    val messages: List<MessageModel> = emptyList()
)
