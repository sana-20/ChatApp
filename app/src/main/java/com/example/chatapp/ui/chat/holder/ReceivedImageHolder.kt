package com.example.chatapp.ui.chat.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.common.HolderEvent
import com.example.chatapp.common.ViewHolder
import com.example.chatapp.databinding.ItemReceivedImageBinding
import com.example.chatapp.ui.chat.model.Chat

class ReceivedImageHolder(view: View) :
   ViewHolder<Chat.Image, ReceivedImageHolder.Event>(view) {

    private val binding = ItemReceivedImageBinding.bind(view)

    override fun bind(item: Chat.Image, event: Event) {
        Glide.with(binding.imgMessage)
            .load(item.url)
            .into(binding.imgMessage)

        //TODO: 채팅방에서 가져온 데이터 활용
        Glide.with(binding.imgProfile)
            .load("https://search.pstatic.net/common?type=f&size=210x236&quality=90&direct=true&src=https%3A%2F%2Fcsearch-phinf.pstatic.net%2F20220713_200%2F16576840580230Fwu8_JPEG%2F19cdc6c7dde407af7c0c8ea680c9004f.jpg")
            .into(binding.imgProfile)
    }

    companion object {
        const val ID = R.layout.item_received_image

        val DIFF = object : DiffUtil.ItemCallback<Chat.Image>() {
            override fun areItemsTheSame(oldItem: Chat.Image, newItem: Chat.Image): Boolean {
                return oldItem.id== newItem.id
            }

            override fun areContentsTheSame(oldItem: Chat.Image, newItem: Chat.Image): Boolean {
                return oldItem == newItem
            }

        }

        fun from(parent: ViewGroup): ReceivedImageHolder {
            return ReceivedImageHolder(
                LayoutInflater.from(parent.context).inflate(ID, parent, false)
            )
        }
    }

    interface Event : HolderEvent {
    }

}