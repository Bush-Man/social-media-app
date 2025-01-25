package com.app.socialmedia.domain.model

import com.app.socialmedia.data.dto.MessageDto


data class MessageModel(
    val id:String,
    val message:String,
    val senderId:String,
    val receiverId:String,
    val roomId:String,
    val timestamp:String
)

fun MessageModel.toMessageDto(): MessageDto {
     return MessageDto(

         id = id,
         message = message,
         senderId =senderId,
         receiverId = receiverId,
         roomId = roomId,
         timestamp = timestamp
     )
}