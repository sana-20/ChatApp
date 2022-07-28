package com.example.chatapp.domain.mapper

import com.example.chatapp.data.local.ChatEntity
import com.example.chatapp.data.local.MessageType
import com.example.chatapp.socket.SocketMessageUtil
import javax.inject.Inject

class ChatMapper @Inject constructor() {
    fun map(text: String, type: MessageType): ChatEntity {
        return when (type) {
            MessageType.RECEIVED_IMAGE -> ChatEntity(
                message = "",
                imageUrl = SocketMessageUtil.getReceivedMessage(text, "image"),
                type = type
            )
            MessageType.RECEIVED_TEXT -> ChatEntity(
                message = SocketMessageUtil.getReceivedMessage(text, "message"),
                imageUrl = "",
                type = type
            )
            MessageType.SEND -> ChatEntity(message = text, imageUrl = "", type = type)
        }
    }
}