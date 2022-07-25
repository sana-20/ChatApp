package com.example.chatapp.domain.mapper

import com.example.chatapp.data.local.ChatEntity
import com.example.chatapp.data.local.MessageType
import com.example.chatapp.ui.chat.model.Chat
import javax.inject.Inject

class ChatMapper @Inject constructor() {
    fun map(from: List<ChatEntity>): List<Chat> {
        return from.map {
            when (it.type) {
                MessageType.RECEIVED -> Chat.Text(id = it.id, it.message.toString())
                MessageType.SEND -> Chat.MyText(id = it.id, it.message.toString())
            }
        }
    }
}