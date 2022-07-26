package com.example.chatapp.socket

import com.example.chatapp.data.local.MessageType
import org.json.JSONObject

object SocketMessageUtil {

    fun getMessageType(text: String): MessageType {
        val jsonObject = JSONObject(text)
        return if (jsonObject.has("message")) {
            MessageType.RECEIVED_TEXT
        } else if (jsonObject.has("image")) {
            MessageType.RECEIVED_IMAGE
        } else {
            throw Exception("cannot find key")
        }
    }

    fun setSendMessage(message: String): String {
        val json = JSONObject()
        json.put("action", "sendmessage")
        json.put("message", message)
        return json.toString()
    }

    fun getReceivedMessage(text: String, key: String): String {
        return JSONObject(text).get(key).toString()
    }

}