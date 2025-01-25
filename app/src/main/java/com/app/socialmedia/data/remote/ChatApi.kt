package com.app.socialmedia.data.remote

import com.app.socialmedia.data.dto.MessageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatApi {
    @GET("messages/{chatRoomId}")
    suspend fun getChatRoomMessages(@Path("chatRoomId") chatRoomId:String):Response<List<MessageDto>>

}