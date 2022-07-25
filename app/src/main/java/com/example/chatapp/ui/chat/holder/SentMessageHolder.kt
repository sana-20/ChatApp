package com.example.chatapp.ui.chat.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.chatapp.R
import com.example.chatapp.common.HolderEvent
import com.example.chatapp.common.ViewHolder
import com.example.chatapp.databinding.ItemSendMessageBinding
import com.example.chatapp.ui.chat.model.Chat

class SentMessageHolder(view: View) :
   ViewHolder<Chat.MyText, SentMessageHolder.Event>(view) {

    private val binding = ItemSendMessageBinding.bind(view)

    override fun bind(item: Chat.MyText, event: Event) {
        binding.tvMessage.text = item.message
    }

    companion object {
        const val ID = R.layout.item_send_message

        val DIFF = object : DiffUtil.ItemCallback<Chat.MyText>() {
            override fun areItemsTheSame(oldItem: Chat.MyText, newItem: Chat.MyText): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Chat.MyText, newItem: Chat.MyText): Boolean {
                return oldItem == newItem
            }
        }

        fun from(parent: ViewGroup): SentMessageHolder {
            return SentMessageHolder(
                LayoutInflater.from(parent.context).inflate(ID, parent, false)
            )
        }
    }

    interface Event : HolderEvent {
    }

}