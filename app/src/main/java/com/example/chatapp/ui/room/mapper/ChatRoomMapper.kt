package com.example.chatapp.ui.room.mapper

import com.example.chatapp.domain.dto.ChatRoomDto
import com.example.chatapp.ui.room.model.ChatRoom
import javax.inject.Inject

class ChatRoomMapper @Inject constructor() {
    fun map(from: ChatRoomDto, message: String) : ChatRoom{
        return ChatRoom(from.name, from.userName, from.profile, message)
    }
}