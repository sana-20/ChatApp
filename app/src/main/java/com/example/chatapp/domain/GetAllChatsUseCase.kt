package com.example.chatapp.domain

import com.example.chatapp.data.ChatRepository
import com.example.chatapp.data.local.ChatEntity
import com.example.chatapp.domain.mapper.ChatMapper
import com.example.chatapp.ui.chat.model.Chat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    private val chatMapper: ChatMapper
) {

    fun invoke(): Flow<List<Chat>> {
        return chatRepository.getAllChats().map {
            chatMapper.map(it)
        }
    }

}