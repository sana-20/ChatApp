package com.example.chatapp.data.remote

data class ApiResponse<T>(
    val statusCode: Int,
    val data: T
)

data class ChatRoomModel(
    val name: String,
    val userName: String,
    val profile: String
)