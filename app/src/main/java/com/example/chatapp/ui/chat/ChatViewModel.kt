package com.example.chatapp.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.local.ChatEntity
import com.example.chatapp.data.local.MessageType
import com.example.chatapp.domain.AddChatUseCase
import com.example.chatapp.domain.GetAllChatsUseCase
import com.example.chatapp.socket.SocketMessageUtil
import com.example.chatapp.ui.UiState
import com.example.chatapp.ui.chat.mapper.ChatMapper
import com.example.chatapp.ui.chat.model.Chat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val addChatUseCase: AddChatUseCase,
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val chatMapper: ChatMapper
) : ViewModel() {

    private lateinit var name: String

    private lateinit var profile: String

    private val _uiState = MutableStateFlow<UiState<List<Chat>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Chat>>> = _uiState

    fun setData(safeArgs: ChatFragmentArgs) {
        name = safeArgs.userName
        profile = safeArgs.profile
    }

    fun load() {
        viewModelScope.launch {
            getAllChatsUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .catch {
                    _uiState.value = UiState.Error
                }
                .collect {
                    val chats = chatMapper.map(it, name, profile)
                    _uiState.value = UiState.Success(chats)
                }
        }
    }

    fun sendButtonClicked(text: String) {
        saveMessage(text, MessageType.SEND)
    }

    fun messageReceived(text: String) {
        saveMessage(text, SocketMessageUtil.getMessageType(text))
    }

    private fun saveMessage(text: String, type: MessageType) {
        CoroutineScope(Dispatchers.IO).launch {
            addChatUseCase.invoke(AddChatUseCase.Param(text, type))
        }
    }

}