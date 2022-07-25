package com.example.chatapp.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentChatRoomBinding
import com.example.chatapp.ui.room.holder.ChatRoomViewHolder
import com.example.chatapp.ui.room.model.ChatRoom
import kotlinx.coroutines.launch

class ChatRoomFragment : Fragment(), ChatRoomViewHolder.Event {

    private var _binding: FragmentChatRoomBinding? = null

    private val binding get() = _binding!!

    private val chatRoomViewModel: ChatRoomViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ChatRoomAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            chatRoomViewModel.uiState.collect {
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickChatRoom(item: ChatRoom) {
        val action = ChatRoomFragmentDirections.actionToChat(item.userName)
        findNavController().navigate(action)
    }

}