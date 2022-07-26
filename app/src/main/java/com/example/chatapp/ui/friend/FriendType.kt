package com.example.chatapp.ui.friend

enum class FriendType(
    val title: String
) {
    FRIENDS("친구"), FAVORITE("즐겨찾기"), RECOMMEND("추천친구");

    override fun toString(): String {
        return name.lowercase()
    }
}