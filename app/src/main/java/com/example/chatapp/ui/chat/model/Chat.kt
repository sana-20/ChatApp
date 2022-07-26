package com.example.chatapp.ui.chat.model

sealed class Chat {

    abstract val id: Int
    abstract val message: String

    data class Text(
        override val id: Int,
        override val message: String,
        val userName: String,
        val profile: String
    ) : Chat()

    data class Image(
        override val id: Int,
        override val message: String,
        val userName: String,
        val profile: String
    ) : Chat()

    data class MyText(
        override val id: Int,
        override val message: String
    ) : Chat()

}
