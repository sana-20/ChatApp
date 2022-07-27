package com.example.chatapp.domain

import com.example.chatapp.common.map
import com.example.chatapp.data.ChatRoomRepository
import com.example.chatapp.data.remote.BaseResult
import com.example.chatapp.domain.dto.ChatRoomDto
import com.example.chatapp.domain.mapper.ChatRoomMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChatRoomUseCase @Inject constructor(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatRoomMapper: ChatRoomMapper
) {
    fun invoke(type: String): Flow<BaseResult<List<ChatRoomDto>>> {
        return chatRoomRepository.getChatRoom(type).map {
            it.map { list ->
                chatRoomMapper.map(list)
            }
        }
    }
}