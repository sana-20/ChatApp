package com.example.chatapp.domain.mapper

import com.example.chatapp.data.remote.FriendModel
import com.example.chatapp.ui.friend.model.Friend
import javax.inject.Inject

class FriendMapper @Inject constructor() {
    fun map(from: List<FriendModel>): List<Friend> {
        return from.map {
            Friend.Profile(it.name, it.profile)
        }
    }
}