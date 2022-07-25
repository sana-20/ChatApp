package com.example.chatapp.domain

import com.example.chatapp.data.ChatRepository
import com.example.chatapp.data.local.ChatEntity
import javax.inject.Inject

class AddChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    suspend fun invoke(chatEntity: ChatEntity) {
        chatRepository.addChat(chatEntity)
    }

}