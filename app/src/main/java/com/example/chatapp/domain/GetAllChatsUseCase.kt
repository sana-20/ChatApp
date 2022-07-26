package com.example.chatapp.domain

import com.example.chatapp.data.ChatRepository
import com.example.chatapp.data.local.ChatEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    fun invoke(): Flow<List<ChatEntity>> {
        return chatRepository.getAllChats()
    }

}