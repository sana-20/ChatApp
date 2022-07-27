package com.example.chatapp.ui.friend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.common.zip
import com.example.chatapp.data.remote.BaseResult
import com.example.chatapp.domain.GetFriendsUseCase
import com.example.chatapp.ui.UiState
import com.example.chatapp.ui.friend.model.Friend
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val friendUseCase: GetFriendsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Friend>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Friend>>> = _uiState

    init {
        viewModelScope.launch {
            val friendsFlow = friendUseCase.invoke(FriendType.FRIENDS)
            val favoriteFlow = friendUseCase.invoke(FriendType.FAVORITE)
            val recommendFlow = friendUseCase.invoke(FriendType.RECOMMEND)

            zip(friendsFlow, favoriteFlow, recommendFlow) { a, b, c ->
                Triple(a, b, c)
            }.flowOn(Dispatchers.IO)
                .catch {
                    _uiState.value = UiState.Error
                }
                .collect { triple ->
                    val (first, second, third) = triple

                    val list = mutableListOf<Friend>()

                    val firstSuccess = when (first) {
                        is BaseResult.Success -> {
                            list.addAll(first.data)
                            true
                        }
                        is BaseResult.Error -> {
                            false
                        }
                    }

                    val secondSuccess = when (second) {
                        is BaseResult.Success -> {
                            list.addAll(second.data)
                            true
                        }
                        is BaseResult.Error -> {
                            false
                        }
                    }

                    val thirdSuccess = when (third) {
                        is BaseResult.Success -> {
                            list.addAll(third.data)
                            true
                        }
                        is BaseResult.Error -> {
                            false
                        }
                    }

                    if (firstSuccess || secondSuccess || thirdSuccess) {
                        _uiState.value = UiState.Success(list)
                    } else {
                        _uiState.value = UiState.Error
                    }
                }
        }
    }

}