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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.data.local.MessageType
import com.example.chatapp.databinding.FragmentChatBinding
import com.example.chatapp.socket.MessageListener
import com.example.chatapp.socket.SocketMessageUtil
import com.example.chatapp.socket.WebSocketManager
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        webSocketManager.init(this)
        CoroutineScope(Dispatchers.IO).launch {
            webSocketManager.connect()
        }

        chatViewModel.setData(safeArgs)

        chatViewModel.getAllMessage()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatAdapter = ChatAdapter(this, this, this)
        binding.recyclerChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnLayoutChangeListener { p0, p1, p2, p3, p4, p5, p6, p7, p8 ->
                scrollToPosition(
                    chatAdapter.itemCount - 1
                )
            }
        }


        lifecycleScope.launch {
            chatViewModel.uiState.collect {
                Log.d(TAG, it.toString())
                chatAdapter.submitList(it) {
                    binding.recyclerChat.scrollToPosition(chatAdapter.itemCount - 1)
                }
            }
        }

        binding.btnSend.setOnClickListener {
            val text = binding.editMessage.text
            webSocketManager.sendMessage(text.toString())
            chatViewModel.saveMessage(text.toString(), MessageType.SEND)
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
        chatViewModel.saveMessage(text, SocketMessageUtil.getMessageType(text))
    }
}