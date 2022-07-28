package com.example.chatapp.domain

import com.example.chatapp.data.ChatRepository
import com.example.chatapp.data.local.ChatEntity
import com.example.chatapp.data.local.MessageType
import com.example.chatapp.domain.mapper.ChatMapper
import javax.inject.Inject

class AddChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    private val chatMapper: ChatMapper
) {

    suspend fun invoke(param: Param) {
        chatRepository.addChat(chatMapper.map(param.text, param.type))
    }

    data class Param(
        val text: String,
        val type: MessageType
    )

}