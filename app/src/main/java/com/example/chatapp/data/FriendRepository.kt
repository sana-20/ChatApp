package com.example.chatapp.data

import com.example.chatapp.data.remote.FriendModel
import com.example.chatapp.data.remote.BaseResult
import kotlinx.coroutines.flow.Flow

interface FriendRepository {
    suspend fun getFriends(type: String): Flow<BaseResult<List<FriendModel>>>
}