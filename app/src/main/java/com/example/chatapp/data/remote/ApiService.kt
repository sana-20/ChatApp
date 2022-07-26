package com.example.chatapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/")
    suspend fun getFriend(@Query("type") type: String): ApiResponse<List<FriendModel>>

    @GET("/")
    suspend fun getChatRoom(@Query("type") type: String): ApiResponse<List<ChatRoomModel>>

}