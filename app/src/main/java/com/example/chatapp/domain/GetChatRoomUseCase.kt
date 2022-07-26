package com.example.chatapp.domain

import com.example.chatapp.data.ChatRoomRepository
import com.example.chatapp.domain.dto.ChatRoomDto
import com.example.chatapp.domain.mapper.ChatRoomMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChatRoomUseCase @Inject constructor(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatRoomMapper: ChatRoomMapper
) {
    fun invoke(type: String): Flow<List<ChatRoomDto>> {
        return chatRoomRepository.getChatRoom(type).map {
            chatRoomMapper.map(it)
        }
    }
}