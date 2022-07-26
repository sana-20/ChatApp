package com.example.chatapp.ui.friend

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.FragmentFriendBinding
import com.example.chatapp.ui.friend.holder.FriendHeaderHolder
import com.example.chatapp.ui.friend.holder.FriendProfileHolder
import com.example.chatapp.ui.friend.model.Friend
import com.example.chatapp.ui.room.ChatRoomAdapter
import com.example.chatapp.ui.room.holder.ChatRoomViewHolder
import com.example.chatapp.ui.room.model.ChatRoom
import kotlinx.coroutines.launch

class FriendFragment : Fragment(), FriendProfileHolder.Event,
    FriendHeaderHolder.Event {

    private var _binding: FragmentFriendBinding? = null

    private val binding get() = _binding!!

    private val friendViewModel: FriendViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FriendAdapter(this, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            friendViewModel.uiState.collect {
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}