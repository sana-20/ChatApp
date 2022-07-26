package com.example.chatapp.data

import com.example.chatapp.data.local.ChatDao
import com.example.chatapp.data.local.ChatEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val dao: ChatDao
) {

    fun getAllChats(): Flow<List<ChatEntity>> {
        return dao.getAllChats()
    }

    suspend fun addChat(chatEntity: ChatEntity) {
        dao.insertChat(chatEntity)
    }

    suspend fun getLastChat(): ChatEntity {
        return dao.getLastChat()
    }

}