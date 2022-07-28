package com.example.chatapp.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.remote.BaseResult
import com.example.chatapp.domain.GetChatRoomUseCase
import com.example.chatapp.ui.UiState
import com.example.chatapp.ui.room.model.ChatRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatRoomUseCase: GetChatRoomUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ChatRoom>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ChatRoom>>> = _uiState

    fun load() {
        viewModelScope.launch {
            chatRoomUseCase.invoke("chats")
                .flowOn(Dispatchers.IO)
                .catch {
                    _uiState.value = UiState.Error
                }
                .collect { result ->
                    when(result) {
                        is BaseResult.Success -> {
                            _uiState.value = UiState.Success(result.data)
                        }
                        is BaseResult.Error -> {
                            _uiState.value = UiState.Error
                        }
                    }
                }
        }
    }

}