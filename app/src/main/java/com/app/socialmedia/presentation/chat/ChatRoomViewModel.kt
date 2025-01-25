package com.app.socialmedia.presentation.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.socialmedia.common.Constants
import com.app.socialmedia.common.Resource
import com.app.socialmedia.data.dto.MessageDto
import com.app.socialmedia.data.dto.toMessageModel
import com.app.socialmedia.domain.use_case.chat.GetChatRoomMessagesUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val getChatRoomMessagesUseCase: GetChatRoomMessagesUseCase
):ViewModel(){


    private val _state = MutableStateFlow(ChatRoomState())
    val state:MutableStateFlow<ChatRoomState> = _state
    private var socket: Socket = IO.socket(Constants.CHAT_BASE_URL)
    val gson =Gson()
    init {

        socket.connect()
        socket.on("newMessage"){response ->
            val newMsg = gson.fromJson(response[0].toString(),MessageDto::class.java)
            val messagesList = state.value.messages.toMutableList()
            messagesList.add(newMsg.toMessageModel())
            _state.update { it.copy(messages = messagesList) }

        }
    }


    fun onChatRoomEvent(event: ChatRoomEvents){
        when(event){
            is ChatRoomEvents.getChatRoomMessages -> {
                getChatRoomMessages(event.chatRoomId)
            }
        }
    }


    private fun getChatRoomMessages(roomId:String){
        viewModelScope.launch {
            getChatRoomMessagesUseCase(roomId).collect{result ->
                when(result){
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        _state.update { it.copy(messages = result.data!! ) }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        socket.disconnect()
        socket.off()
    }
}