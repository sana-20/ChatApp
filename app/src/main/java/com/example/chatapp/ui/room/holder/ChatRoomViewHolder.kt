package com.example.chatapp.ui.room.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.common.HolderEvent
import com.example.chatapp.common.ViewHolder
import com.example.chatapp.databinding.ItemChatRoomBinding
import com.example.chatapp.ui.room.model.ChatRoom

class ChatRoomViewHolder(view: View) :
   ViewHolder<ChatRoom, ChatRoomViewHolder.Event>(view) {

    private val binding = ItemChatRoomBinding.bind(view)

    override fun bind(item: ChatRoom, event: Event) {
        Glide.with(binding.imageView)
            .load(item.profile)
            .into(binding.imageView)

        binding.tvName.text = item.name

        binding.root.setOnClickListener {
            event.onClickChatRoom(item)
        }
    }

    companion object {
        const val ID = R.layout.item_chat_room

        val DIFF = object : DiffUtil.ItemCallback<ChatRoom>() {
            override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
                return oldItem == newItem
            }
        }

        fun from(parent: ViewGroup): ChatRoomViewHolder {
            return ChatRoomViewHolder(
                LayoutInflater.from(parent.context).inflate(ID, parent, false)
            )
        }
    }

    interface Event : HolderEvent {
        fun onClickChatRoom(item: ChatRoom)
    }

}