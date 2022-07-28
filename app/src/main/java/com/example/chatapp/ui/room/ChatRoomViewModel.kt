package com.example.chatapp.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.common.map
import com.example.chatapp.data.remote.BaseResult
import com.example.chatapp.domain.GetChatRoomUseCase
import com.example.chatapp.domain.GetLastChatUseCase
import com.example.chatapp.ui.UiState
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

    private val _uiState = MutableStateFlow<UiState<List<ChatRoom>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ChatRoom>>> = _uiState

    fun load() {
        viewModelScope.launch {
            chatRoomUseCase.invoke("chats")
                .combine(getLastChatUseCase.invoke()) { result, chatEntity ->
                    val message = if (chatEntity==null) "" else chatEntity.message.toString()
                    result.map { list ->
                        list.map {
                            chatRoomMapper.map(it, message)
                        }
                    }
                }
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