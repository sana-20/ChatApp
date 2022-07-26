package com.example.chatapp.domain

import com.example.chatapp.data.ChatRepository
import javax.inject.Inject

class GetLastChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    suspend fun invoke(): String {
        return chatRepository.getLastChat().message.toString()
    }

}