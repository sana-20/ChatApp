package com.example.chatapp.domain.mapper

import com.example.chatapp.data.remote.ChatRoomModel
import com.example.chatapp.ui.room.model.ChatRoom
import javax.inject.Inject

class ChatRoomMapper @Inject constructor() {
    fun map(from: List<ChatRoomModel>): List<ChatRoom> {
        return from.map {
            ChatRoom(name = it.name, userName = it.userName, profile = it.profile)
        }
    }
}