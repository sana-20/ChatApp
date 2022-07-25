package com.example.chatapp.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.local.ChatEntity
import com.example.chatapp.data.local.MessageType
import com.example.chatapp.domain.AddChatUseCase
import com.example.chatapp.domain.GetAllChatsUseCase
import com.example.chatapp.socket.WebSocketManager
import com.example.chatapp.ui.chat.model.Chat
import com.example.chatapp.ui.room.model.ChatRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val addChatUseCase: AddChatUseCase,
    private val getAllChatsUseCase: GetAllChatsUseCase
) : ViewModel() {

    private val _message = MutableStateFlow("")

    private val _name = MutableStateFlow("")

    private val _uiState = MutableStateFlow<List<Chat>>(emptyList())
    val uiState: StateFlow<List<Chat>> = _uiState


    fun setFriendName(name: String) {
        _name.value = name
    }

    fun setMessage(message: String) {
        _message.value = message
    }

    fun sendMessage() {
        if (_message.value.isEmpty()) return

        //TODO: SocketMessageUtil 이용
        val json = JSONObject()
        json.put("action", "sendmessage")
        json.put("message", _message.value)

        WebSocketManager.sendMessage(json.toString())

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
                    _uiState.value = it
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

        saveMessage(
            ChatEntity(
                message = JSONObject(text).get("message").toString(),
                imageUrl = "",
                type = MessageType.RECEIVED
            )
        )
    }


}