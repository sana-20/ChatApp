package com.example.chatapp.domain.mapper

import com.example.chatapp.data.remote.FriendModel
import com.example.chatapp.ui.friend.FriendType
import com.example.chatapp.ui.friend.model.Friend
import javax.inject.Inject

class FriendMapper @Inject constructor() {
    fun map(from: List<FriendModel>, type: FriendType): List<Friend> {
        val list = mutableListOf<Friend>()
        val profileList = from.map {
            Friend.Profile(it.name, it.profile)
        }
        list.add(Friend.Header(type.title))
        list.addAll(profileList)
        return list
    }
}