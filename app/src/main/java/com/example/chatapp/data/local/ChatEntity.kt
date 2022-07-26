package com.example.chatapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chatapp.data.local.AppDatabase.Companion.TABLE_NAME_CHAT

@Entity(tableName = TABLE_NAME_CHAT)
data class ChatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val message: String?,
    val imageUrl: String?,
    val type: MessageType
)

enum class MessageType {
    RECEIVED_IMAGE, RECEIVED_TEXT, SEND
}