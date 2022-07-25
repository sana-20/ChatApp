package com.example.chatapp.domain

import com.example.chatapp.data.ChatRoomRepository
import com.example.chatapp.data.remote.ChatRoomModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatRoomUseCase @Inject constructor(
    private val chatRoomRepository: ChatRoomRepository
) {
    fun invoke(type: String): Flow<List<ChatRoomModel>> {
        return chatRoomRepository.getChatRoom(type)
    }
}