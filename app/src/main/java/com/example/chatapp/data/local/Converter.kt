package com.example.chatapp.data.local

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun toMessageType(value: String) = enumValueOf<MessageType>(value)

    @TypeConverter
    fun fromMessageType(value: MessageType) = value.name

}