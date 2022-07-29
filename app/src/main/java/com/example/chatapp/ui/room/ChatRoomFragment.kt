package com.example.chatapp.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.FragmentChatRoomBinding
import com.example.chatapp.ui.UiState
import com.example.chatapp.ui.room.holder.ChatRoomViewHolder
import com.example.chatapp.ui.room.model.ChatRoom
import kotlinx.coroutines.launch

class ChatRoomFragment : Fragment(), ChatRoomViewHolder.Event {

    private var _binding: FragmentChatRoomBinding? = null

    private val binding get() = _binding!!

    private val chatRoomViewModel: ChatRoomViewModel by activityViewModels()

    private lateinit var adapter: ChatRoomAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        observeState()
        load()
    }

    private fun load() {
        chatRoomViewModel.load()
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatRoomViewModel.uiState.collect {
                    when (it) {
                        UiState.Error -> {}
                        UiState.Loading -> {}
                        is UiState.Success -> adapter.submitList(it.data)
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        adapter = ChatRoomAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickChatRoom(item: ChatRoom) {
        val action = ChatRoomFragmentDirections.actionToChat(item.userName, item.profile)
        findNavController().navigate(action)
    }

}