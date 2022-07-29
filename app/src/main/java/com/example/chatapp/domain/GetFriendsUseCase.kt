package com.example.chatapp.domain

import com.example.chatapp.common.zip
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
    suspend fun invoke(): Flow<Triple<BaseResult<List<Friend>>, BaseResult<List<Friend>>, BaseResult<List<Friend>>>> {
        val friendsFlow = friendRepository.getFriends(FriendType.FRIENDS.toString())
            .map {
                friendMapper.map(it, FriendType.FRIENDS)
            }

        val favoriteFlow = friendRepository.getFriends(FriendType.FAVORITE.toString())
            .map {
                friendMapper.map(it, FriendType.FAVORITE)
            }
        val recommendFlow = friendRepository.getFriends(FriendType.RECOMMEND.toString())
            .map {
                friendMapper.map(it, FriendType.RECOMMEND)
            }

        return zip(friendsFlow, favoriteFlow, recommendFlow) { a, b, c ->
            Triple(a, b, c)
        }
    }
}