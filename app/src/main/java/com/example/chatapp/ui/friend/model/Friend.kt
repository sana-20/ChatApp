package com.example.chatapp.ui.friend.model

sealed class Friend {

    data class Profile(
        val name: String,
        val profile: String
    ): Friend()

    data class Header(
        val title: String
    ): Friend()

}