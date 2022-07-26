package com.example.chatapp.ui.friend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.GetFriendsUseCase
import com.example.chatapp.ui.friend.model.Friend
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val friendUseCase: GetFriendsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<Friend>>(emptyList())
    val uiState: StateFlow<List<Friend>> = _uiState

    init {
        viewModelScope.launch {
            friendUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .catch {

                }
                .collect {
                    _uiState.value = it
                }
        }
    }

}