package com.example.chatapp.ui.friend.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.chatapp.R
import com.example.chatapp.common.HolderEvent
import com.example.chatapp.common.ViewHolder
import com.example.chatapp.databinding.ItemFriendHeaderBinding
import com.example.chatapp.ui.friend.model.Friend

class FriendHeaderHolder(view: View) :
   ViewHolder<Friend.Header, FriendHeaderHolder.Event>(view) {

    private val binding = ItemFriendHeaderBinding.bind(view)

    override fun bind(item: Friend.Header, event: Event) {
        binding.tvTitle.text = item.title
    }

    companion object {
        const val ID = R.layout.item_friend_header

        val DIFF = object : DiffUtil.ItemCallback<Friend.Header>() {
            override fun areItemsTheSame(oldItem: Friend.Header, newItem: Friend.Header): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Friend.Header, newItem: Friend.Header): Boolean {
                return oldItem == newItem
            }
        }

        fun from(parent: ViewGroup): FriendHeaderHolder {
            return FriendHeaderHolder(
                LayoutInflater.from(parent.context).inflate(ID, parent, false)
            )
        }
    }

    interface Event : HolderEvent {
    }

}