package com.example.chatapp.ui.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.local.ChatEntity
import com.example.chatapp.data.local.MessageType
import com.example.chatapp.domain.AddChatUseCase
import com.example.chatapp.domain.GetAllChatsUseCase
import com.example.chatapp.socket.SocketMessageUtil
import com.example.chatapp.socket.WebSocketManager
import com.example.chatapp.ui.chat.mapper.ChatMapper
import com.example.chatapp.ui.chat.model.Chat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val addChatUseCase: AddChatUseCase,
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val chatMapper: ChatMapper
) : ViewModel() {

    private val _message = MutableStateFlow("")

    private lateinit var name : String

    private lateinit var profile : String

    private val _uiState = MutableStateFlow<List<Chat>>(emptyList())
    val uiState: StateFlow<List<Chat>> = _uiState


    fun setData(safeArgs: ChatFragmentArgs) {
        name = safeArgs.userName
        profile = safeArgs.profile
    }

    fun setMessage(message: String) {
        _message.value = message
    }

    fun sendMessage() {
        if (_message.value.isEmpty()) return

        val jsonString = SocketMessageUtil.setSendMessage(_message.value)
        WebSocketManager.sendMessage(jsonString)

        saveMessage(
            ChatEntity(
                message = _message.value,
                imageUrl = "",
                type = MessageType.SEND
            )
        )
    }

    private fun saveMessage(chat: ChatEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            addChatUseCase.invoke(chat)
        }
    }

    fun getAllMessage() {
        viewModelScope.launch {
            getAllChatsUseCase.invoke()
                .collect {
                    val chats = chatMapper.map(it, name, profile)
                    _uiState.value = chats
                }
        }
    }

    fun connect() {
        CoroutineScope(Dispatchers.IO).launch {
            WebSocketManager.connect()
        }
    }

    fun close() {
        WebSocketManager.close()
    }

    fun receivedMessage(text: String?) {
        if (text.isNullOrEmpty()) return
        Log.d("로그", text)
        saveReceivedMessage(text)
    }

    private fun saveReceivedMessage(text: String) {
        when (SocketMessageUtil.getMessageType(text)) {
            MessageType.RECEIVED_IMAGE -> {
                saveMessage(
                    ChatEntity(
                        message = "",
                        imageUrl = SocketMessageUtil.getReceivedMessage(text, "image"),
                        type = MessageType.RECEIVED_IMAGE
                    )
                )
            }
            MessageType.RECEIVED_TEXT -> {
                saveMessage(
                    ChatEntity(
                        message = SocketMessageUtil.getReceivedMessage(text, "message"),
                        imageUrl = "",
                        type = MessageType.RECEIVED_TEXT
                    )
                )
            }
            else -> {

            }
        }
    }

}