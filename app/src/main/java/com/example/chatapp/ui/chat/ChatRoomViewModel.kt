package com.example.chatapp.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.remote.ChatRoomModel
import com.example.chatapp.domain.GetChatRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatRoomUseCase: GetChatRoomUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<ChatRoomModel>>(emptyList())
    val uiState: StateFlow<List<ChatRoomModel>> = _uiState

    init {
        viewModelScope.launch {
            chatRoomUseCase.invoke("chats")
                .collect {
                    _uiState.value = it
                }
        }
    }

}