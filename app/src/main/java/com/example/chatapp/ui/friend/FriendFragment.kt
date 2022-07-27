package com.example.chatapp.ui.friend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.FragmentFriendBinding
import com.example.chatapp.ui.UiState
import com.example.chatapp.ui.friend.holder.FriendHeaderHolder
import com.example.chatapp.ui.friend.holder.FriendProfileHolder
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
                when (it) {
                    UiState.Error -> {}
                    UiState.Loading -> {}
                    is UiState.Success -> adapter.submitList(it.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}