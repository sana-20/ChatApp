package com.example.chatapp.ui.chat

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.chatapp.common.ViewHolder
import com.example.chatapp.ui.chat.holder.ReceivedImageHolder
import com.example.chatapp.ui.chat.holder.ReceivedTextHolder
import com.example.chatapp.ui.chat.holder.SentMessageHolder
import com.example.chatapp.ui.chat.model.Chat

class ChatAdapter(
    private val textChatAction: ReceivedTextHolder.Event,
    private val imageChatAction: ReceivedImageHolder.Event,
    private val myTextChatAction: SentMessageHolder.Event,
) : ListAdapter<Chat, ViewHolder<*, *>>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*, *> {
        return when (viewType) {
            ReceivedTextHolder.ID -> ReceivedTextHolder.from(parent)
            ReceivedImageHolder.ID -> ReceivedImageHolder.from(parent)
            SentMessageHolder.ID -> SentMessageHolder.from(parent)
            else -> throw Exception("It is not a supported type.")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<*, *>, position: Int) {
        when (holder) {
            is ReceivedTextHolder -> holder.bind(
                getItem(position) as Chat.Text,
                textChatAction
            )
            is ReceivedImageHolder -> holder.bind(
                getItem(position) as Chat.Image,
                imageChatAction
            )
            is SentMessageHolder -> holder.bind(
                getItem(position) as Chat.MyText,
                myTextChatAction
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Chat.Text -> ReceivedTextHolder.ID
            is Chat.Image -> ReceivedImageHolder.ID
            is Chat.MyText -> SentMessageHolder.ID
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return try {
                    when (oldItem) {
                        is Chat.Text -> ReceivedTextHolder.DIFF.areItemsTheSame(
                            oldItem,
                            newItem as Chat.Text
                        )
                        is Chat.Image -> ReceivedImageHolder.DIFF.areItemsTheSame(
                            oldItem,
                            newItem as Chat.Image
                        )
                        is Chat.MyText -> SentMessageHolder.DIFF.areItemsTheSame(
                            oldItem,
                            newItem as Chat.MyText
                        )
                    }
                } catch (e: Throwable) {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return try {
                    when (oldItem) {
                        is Chat.Text -> ReceivedTextHolder.DIFF.areContentsTheSame(
                            oldItem,
                            newItem as Chat.Text
                        )
                        is Chat.Image -> ReceivedImageHolder.DIFF.areContentsTheSame(
                            oldItem,
                            newItem as Chat.Image
                        )
                        is Chat.MyText -> SentMessageHolder.DIFF.areContentsTheSame(
                            oldItem,
                            newItem as Chat.MyText
                        )
                    }
                } catch (e: Throwable) {
                    false
                }
            }
        }
    }
}