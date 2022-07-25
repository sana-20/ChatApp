package com.example.chatapp.ui.chat.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.common.HolderEvent
import com.example.chatapp.common.ViewHolder
import com.example.chatapp.databinding.ItemChatRoomBinding
import com.example.chatapp.databinding.ItemReceivedTextBinding
import com.example.chatapp.ui.chat.model.Chat

class ReceivedTextHolder(view: View) :
    ViewHolder<Chat.Text, ReceivedTextHolder.Event>(view) {

    private val binding = ItemReceivedTextBinding.bind(view)

    override fun bind(item: Chat.Text, event: Event) {
        binding.tvMessage.text = item.message

        Glide.with(binding.imgProfile)
            .load("https://search.pstatic.net/common?type=f&size=210x236&quality=90&direct=true&src=https%3A%2F%2Fcsearch-phinf.pstatic.net%2F20220713_200%2F16576840580230Fwu8_JPEG%2F19cdc6c7dde407af7c0c8ea680c9004f.jpg")
            .into(binding.imgProfile)
    }

    companion object {
        const val ID = R.layout.item_received_text

        val DIFF = object : DiffUtil.ItemCallback<Chat.Text>() {
            override fun areItemsTheSame(oldItem: Chat.Text, newItem: Chat.Text): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Chat.Text, newItem: Chat.Text): Boolean {
                return oldItem == newItem
            }
        }

        fun from(parent: ViewGroup): ReceivedTextHolder {
            return ReceivedTextHolder(
                LayoutInflater.from(parent.context).inflate(ID, parent, false)
            )
        }
    }

    interface Event : HolderEvent {
    }

}