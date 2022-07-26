package com.example.chatapp.ui.friend

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.chatapp.common.ViewHolder
import com.example.chatapp.ui.friend.holder.FriendHeaderHolder
import com.example.chatapp.ui.friend.holder.FriendProfileHolder
import com.example.chatapp.ui.friend.model.Friend

class FriendAdapter(
    private val profileEvent: FriendProfileHolder.Event,
    private val headerEvent: FriendHeaderHolder.Event
) : ListAdapter<Friend, ViewHolder<*, *>>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*, *> {
        return when (viewType) {
            FriendProfileHolder.ID -> FriendProfileHolder.from(parent)
            FriendHeaderHolder.ID -> FriendHeaderHolder.from(parent)
            else -> throw Exception("It is not a supported type.")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<*, *>, position: Int) {
        when (holder) {
            is FriendProfileHolder -> holder.bind(
                getItem(position) as Friend.Profile,
                profileEvent
            )
            is FriendHeaderHolder -> holder.bind(
                getItem(position) as Friend.Header,
                headerEvent
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Friend.Profile -> FriendProfileHolder.ID
            is Friend.Header -> FriendHeaderHolder.ID
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Friend>() {
            override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
                return try {
                    when (oldItem) {
                        is Friend.Profile -> FriendProfileHolder.DIFF.areItemsTheSame(
                            oldItem,
                            newItem as Friend.Profile
                        )
                        is Friend.Header -> FriendHeaderHolder.DIFF.areItemsTheSame(
                            oldItem,
                            newItem as Friend.Header
                        )
                    }
                } catch (e: Throwable) {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
                return try {
                    when (oldItem) {
                        is Friend.Profile -> FriendProfileHolder.DIFF.areContentsTheSame(
                            oldItem,
                            newItem as Friend.Profile
                        )
                        is Friend.Header -> FriendHeaderHolder.DIFF.areContentsTheSame(
                            oldItem,
                            newItem as Friend.Header
                        )
                    }
                } catch (e: Throwable) {
                    false
                }
            }
        }
    }
}