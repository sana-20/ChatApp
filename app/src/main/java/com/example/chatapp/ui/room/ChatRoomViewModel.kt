package com.example.chatapp.ui.room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.GetChatRoomUseCase
import com.example.chatapp.domain.GetLastChatUseCase
import com.example.chatapp.ui.room.mapper.ChatRoomMapper
import com.example.chatapp.ui.room.model.ChatRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatRoomUseCase: GetChatRoomUseCase,
    private val getLastChatUseCase: GetLastChatUseCase,
    private val chatRoomMapper: ChatRoomMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<ChatRoom>>(emptyList())
    val uiState: StateFlow<List<ChatRoom>> = _uiState

    init {
        viewModelScope.launch {
            chatRoomUseCase.invoke("chats")
                .combine(getLastChatUseCase.invoke()) { chatRoom, chatEntity ->
                    chatRoom.map {
                        chatRoomMapper.map(it, chatEntity.message.toString())
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _uiState.value = it
                }
        }
    }

}