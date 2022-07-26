package com.example.chatapp.data

import com.example.chatapp.data.remote.FriendModel

interface FriendRepository {
    suspend fun getFriends(type: String): List<FriendModel>
}