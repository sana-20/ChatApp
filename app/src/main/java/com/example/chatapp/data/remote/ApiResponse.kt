package com.example.chatapp.data.remote

data class ApiResponse(
    val statusCode: Int,
    val data: List<ChatRoomModel>
)

data class ChatRoomModel(
    val name: String,
    val userName: String,
    val profile: String
)

//data class ApiResponse<T : Data>(
//    val statusCode: Int,
//    val data: List<T>
//)
//
//sealed class Data {
//    data class Friend(
//        val name: String,
//        val profile: String
//    ) : Data()
//
//    data class ChatRoom(
//        val name: String,
//        val userName: String,
//        val profile: String
//    ) : Data()
//}
