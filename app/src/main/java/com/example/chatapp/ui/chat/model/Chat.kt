package com.example.chatapp.ui.chat.model

sealed class Chat {

    abstract val id: Int

    data class Text(
        override val id: Int,
        val message: String
    ) : Chat()

    data class Image(
        override val id: Int,
        val url: String
    ) : Chat()

    data class MyText(
        override val id: Int,
        val message: String
    ) : Chat()

}
