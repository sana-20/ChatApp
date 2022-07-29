package com.example.chatapp.ui.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.FragmentChatBinding
import com.example.chatapp.socket.MessageListener
import com.example.chatapp.socket.WebSocketManager
import com.example.chatapp.ui.UiState
import com.example.chatapp.ui.chat.holder.ReceivedImageHolder
import com.example.chatapp.ui.chat.holder.ReceivedTextHolder
import com.example.chatapp.ui.chat.holder.SentMessageHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatFragment : Fragment(), MessageListener, ReceivedTextHolder.Event,
    ReceivedImageHolder.Event, SentMessageHolder.Event {

    private val TAG = ChatFragment::class.java.simpleName

    private var _binding: FragmentChatBinding? = null

    private val binding get() = _binding!!

    private val chatViewModel: ChatViewModel by activityViewModels()

    private val safeArgs: ChatFragmentArgs by navArgs()

    private val webSocketManager = WebSocketManager()

    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectSocket()
        initView()
        setAdapter()
        load()
        observeState()
    }

    private fun connectSocket() {
        webSocketManager.init(this)
        CoroutineScope(Dispatchers.IO).launch {
            webSocketManager.connect()
        }
    }

    private fun load() {
        chatViewModel.setData(safeArgs)
        chatViewModel.load()
    }

    private fun initView() {
        binding.btnSend.setOnClickListener {
            val text = binding.editMessage.text
            webSocketManager.sendMessage(text.toString())
            chatViewModel.sendButtonClicked(text.toString())
            text.clear()
        }

        binding.editMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.btnSend.isEnabled = p0.toString().isNotEmpty()
            }
        })
    }

    private fun observeState() {
        lifecycleScope.launch {
            chatViewModel.uiState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                when (it) {
                    UiState.Error -> { }
                    UiState.Loading -> { }
                    is UiState.Success -> {
                        chatAdapter.submitList(it.data) {
                            binding.recyclerChat.scrollToPosition(chatAdapter.itemCount - 1)
                        }
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        chatAdapter = ChatAdapter(this, this, this)
        binding.recyclerChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnLayoutChangeListener { p0, p1, p2, p3, p4, p5, p6, p7, p8 ->
                scrollToPosition(
                    chatAdapter.itemCount - 1
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        webSocketManager.close()
    }

    override fun onConnectSuccess() {
        Log.d(TAG, "onConnectSuccess")
    }

    override fun onConnectFailed() {
        Log.d(TAG, "onConnectFailed")
    }

    override fun onClose() {
        Log.d(TAG, "onClose")
    }

    override fun onMessage(text: String?) {
        if (text.isNullOrEmpty()) return
        chatViewModel.messageReceived(text)
    }
}