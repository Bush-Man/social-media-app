package com.app.socialmedia.presentation.chat

sealed class ChatRoomEvents {
    data class getChatRoomMessages(val chatRoomId: String) : ChatRoomEvents()
}
