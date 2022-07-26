package com.example.chatapp.ui.chat.mapper

import com.example.chatapp.data.local.ChatEntity
import com.example.chatapp.data.local.MessageType
import com.example.chatapp.ui.chat.model.Chat
import javax.inject.Inject

class ChatMapper @Inject constructor() {
    fun map(from: List<ChatEntity>, name: String, profile: String): List<Chat> {
        return from.map {
            when (it.type) {
                MessageType.RECEIVED_IMAGE -> Chat.Image(id = it.id, message = it.imageUrl.toString(), userName = name, profile = profile)
                MessageType.RECEIVED_TEXT -> Chat.Text(id = it.id, message = it.message.toString(), userName = name, profile = profile)
                MessageType.SEND -> Chat.MyText(id = it.id, it.message.toString())
            }
        }
    }
}