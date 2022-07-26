package com.example.chatapp.domain

import com.example.chatapp.data.FriendRepository
import com.example.chatapp.data.remote.FriendModel
import com.example.chatapp.domain.mapper.FriendMapper
import com.example.chatapp.ui.friend.FriendType
import com.example.chatapp.ui.friend.model.Friend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFriendsUseCase @Inject constructor(
    private val friendRepository: FriendRepository,
    private val friendMapper: FriendMapper
) {
    suspend fun invoke(): Flow<List<Friend>> {
        val list = withContext(Dispatchers.IO) {
            val deferredFriends =
                async { friendRepository.getFriends(FriendType.FRIENDS.toString()) }
            val deferredFavorite =
                async { friendRepository.getFriends(FriendType.FAVORITE.toString()) }
            val deferredRecommend =
                async { friendRepository.getFriends(FriendType.RECOMMEND.toString()) }

            val friends = deferredFriends.await()
            val favorites = deferredFavorite.await()
            val recommends = deferredRecommend.await()

            buildList {
                add(Friend.Header(FriendType.FRIENDS.title))
                addAll(friendMapper.map(friends))
                add(Friend.Header(FriendType.FAVORITE.title))
                addAll(friendMapper.map(favorites))
                add(Friend.Header(FriendType.RECOMMEND.title))
                addAll(friendMapper.map(recommends))
            }
        }

        return flow {
            emit(list)
        }
    }
}