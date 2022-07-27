package com.example.chatapp.domain

import com.example.chatapp.common.map
import com.example.chatapp.data.FriendRepository
import com.example.chatapp.data.remote.BaseResult
import com.example.chatapp.domain.mapper.FriendMapper
import com.example.chatapp.ui.friend.FriendType
import com.example.chatapp.ui.friend.model.Friend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFriendsUseCase @Inject constructor(
    private val friendRepository: FriendRepository,
    private val friendMapper: FriendMapper
) {
    suspend fun invoke(type: FriendType): Flow<BaseResult<List<Friend>>> {
        return friendRepository.getFriends(type.toString()).map {
            it.map { list ->
                friendMapper.map(list, type)
            }
        }
    }
}