package com.app.socialmedia.presentation.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

data class Message(
    val senderId:String,
    val message:String,
    val timestamp: String
)
@Composable
fun ChatRoomScreen(
    chatRoomId: String,
    chatRoomViewModel: ChatRoomViewModel = hiltViewModel()
) {
    val loggedInUserId = "6" // Assume this is the logged-in user's ID
    val uiState = chatRoomViewModel.state.collectAsState()
    val uiEvents = chatRoomViewModel::onChatRoomEvent
    val messages = uiState.value.messages
    

    if (chatRoomId.isNotEmpty()) {
        LaunchedEffect(chatRoomId) {
            uiEvents(ChatRoomEvents.getChatRoomMessages(chatRoomId))
        }
    } else {
        Log.e("ChatRoomScreen", "No chat room ID provided")
    }

    if (messages.isNotEmpty()) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                items(messages) { message_item ->
                    ChatBubble(
                        message = message_item.message,
                        isSentByUser = message_item.senderId == loggedInUserId,
                        timestamp = message_item.timestamp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "You don't have any conversation yet.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ChatBubble(message: String, isSentByUser: Boolean, timestamp: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isSentByUser) Arrangement.End else Arrangement.Start
    ) {
        Column(
            horizontalAlignment = if (isSentByUser) Alignment.End else Alignment.Start,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (isSentByUser) Color(0xFF2979FF) else Color(0xFFECEFF1),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)

            ) {
                Text(
                    text = message,
                    color = if (isSentByUser){
                        Color.White} else Color.Black,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = timestamp,
                fontSize = 10.sp,
                color = Color.LightGray
            )
        }
    }
}
