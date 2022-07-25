package com.example.chatapp.data

import com.example.chatapp.data.remote.ChatRoomModel
import kotlinx.coroutines.flow.Flow

interface ChatRoomRepository {
    fun getChatRoom(type: String): Flow<List<ChatRoomModel>>
}