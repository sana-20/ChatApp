package com.example.chatapp.data

import com.example.chatapp.data.remote.ApiService
import com.example.chatapp.data.remote.ChatRoomModel
import com.example.chatapp.data.remote.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChatRoomRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ChatRoomRepository {

    override fun getChatRoom(type: String): Flow<BaseResult<List<ChatRoomModel>>> {
        return flow {
            val response = apiService.getChatRoom(type)
            if (response.isSuccessful) {
                emit(BaseResult.Success(response.body()!!.data))
            } else {
                emit(BaseResult.Error(response.errorBody()))
            }
        }
    }

}