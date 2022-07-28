package com.example.chatapp.domain

import com.example.chatapp.common.map
import com.example.chatapp.data.ChatRepository
import com.example.chatapp.data.ChatRoomRepository
import com.example.chatapp.data.remote.BaseResult
import com.example.chatapp.domain.mapper.ChatRoomMapper
import com.example.chatapp.ui.room.model.ChatRoom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetChatRoomUseCase @Inject constructor(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatRepository: ChatRepository,
    private val chatRoomMapper: ChatRoomMapper
) {
    fun invoke(type: String): Flow<BaseResult<List<ChatRoom>>> {
        return chatRoomRepository.getChatRoom(type)
            .combine(chatRepository.getLastChat()) { result, chatEntity ->
                val message = if (chatEntity == null) "" else chatEntity.message.toString()
                result.map { chatRoom ->
                    chatRoomMapper.map(chatRoom, message)
                }
            }
    }
}