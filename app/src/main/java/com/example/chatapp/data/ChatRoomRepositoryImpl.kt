package com.example.chatapp.data

import com.example.chatapp.data.remote.ApiService
import com.example.chatapp.data.remote.ChatRoomModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChatRoomRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ChatRoomRepository {

    override fun getChatRoom(type: String): Flow<List<ChatRoomModel>> {
        return flow {
            emit(apiService.getChatRoom(type).data)
        }
    }
}