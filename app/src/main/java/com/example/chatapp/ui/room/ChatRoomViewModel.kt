package com.example.chatapp.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.remote.ChatRoomModel
import com.example.chatapp.domain.GetChatRoomUseCase
import com.example.chatapp.domain.GetLastChatUseCase
import com.example.chatapp.ui.room.model.ChatRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatRoomUseCase: GetChatRoomUseCase,
    private val getLastChatUseCase: GetLastChatUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<ChatRoom>>(emptyList())
    val uiState: StateFlow<List<ChatRoom>> = _uiState

    init {
        viewModelScope.launch {
            chatRoomUseCase.invoke("chats")
                .collect {
                    _uiState.value = it
                    //getLastChatUseCase.invoke()
                }
        }
    }

}