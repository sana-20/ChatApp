package com.example.chatapp.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface HolderEvent

abstract class ViewHolder<T, A : HolderEvent>(view: View) :
    RecyclerView.ViewHolder(view) {
        abstract fun bind(item: T, event: A)
}
