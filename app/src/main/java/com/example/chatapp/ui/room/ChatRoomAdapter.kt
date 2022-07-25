package com.example.chatapp.ui.room

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.chatapp.ui.room.holder.ChatRoomViewHolder
import com.example.chatapp.ui.room.holder.ChatRoomViewHolder.Companion.DIFF
import com.example.chatapp.ui.room.model.ChatRoom

class ChatRoomAdapter(
    private val chatRoomEvent: ChatRoomViewHolder.Event
) : ListAdapter<ChatRoom, ChatRoomViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        return ChatRoomViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        holder.bind(getItem(position), chatRoomEvent)
    }

}