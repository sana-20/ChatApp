package com.example.chatapp.domain

import com.example.chatapp.data.ChatRepository
import com.example.chatapp.data.local.ChatEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLastChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    fun invoke(): String {
        return chatRepository.getLastChat().message.toString()
    }

}