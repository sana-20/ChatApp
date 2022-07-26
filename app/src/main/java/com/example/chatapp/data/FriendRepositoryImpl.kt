package com.example.chatapp.data

import com.example.chatapp.data.remote.ApiService
import com.example.chatapp.data.remote.FriendModel
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : FriendRepository {

    override suspend fun getFriends(type: String): List<FriendModel> {
        val response = apiService.getFriend(type)
        if (response.statusCode == 200) {
            return response.data
        } else {
            // TODO: 예외처리
            throw Exception("Network error")
        }
    }

}