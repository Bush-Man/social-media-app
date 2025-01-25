package com.app.socialmedia.data.dto

import com.app.socialmedia.domain.model.MessageModel
import com.google.gson.annotations.SerializedName


data class MessageDto(
    @SerializedName("_id")
    val id:String,
    val message:String,
    val senderId:String,
    val receiverId:String,
    val roomId:String,
    val timestamp:String
)

fun MessageDto.toMessageModel():MessageModel{
    return MessageModel(
        id = id,
        message = message,
        senderId =senderId,
        receiverId = receiverId,
        roomId = roomId,
        timestamp = timestamp
    )
}