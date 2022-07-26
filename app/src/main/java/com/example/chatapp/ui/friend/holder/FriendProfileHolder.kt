package com.example.chatapp.ui.friend.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.common.HolderEvent
import com.example.chatapp.common.ViewHolder
import com.example.chatapp.databinding.ItemFriendProfileBinding
import com.example.chatapp.ui.friend.model.Friend

class FriendProfileHolder(view: View) :
   ViewHolder<Friend.Profile, FriendProfileHolder.Event>(view) {

    private val binding = ItemFriendProfileBinding.bind(view)

    override fun bind(item: Friend.Profile, event: Event) {
        binding.tvName.text = item.name
        Glide.with(binding.imageView)
            .load(item.profile)
            .into(binding.imageView)
    }

    companion object {
        const val ID = R.layout.item_friend_profile

        val DIFF = object : DiffUtil.ItemCallback<Friend.Profile>() {
            override fun areItemsTheSame(
                oldItem: Friend.Profile,
                newItem: Friend.Profile
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: Friend.Profile,
                newItem: Friend.Profile
            ): Boolean {
                return oldItem == newItem
            }
        }

        fun from(parent: ViewGroup): FriendProfileHolder {
            return FriendProfileHolder(
                LayoutInflater.from(parent.context).inflate(ID, parent, false)
            )
        }
    }

    interface Event : HolderEvent {
    }

}