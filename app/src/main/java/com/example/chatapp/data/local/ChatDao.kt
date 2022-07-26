package com.example.chatapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chatapp.data.local.AppDatabase.Companion.TABLE_NAME_CHAT
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM $TABLE_NAME_CHAT ORDER BY id ASC")
    fun getAllChats(): Flow<List<ChatEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChat(chatEntity: ChatEntity)

    @Query("SELECT * FROM $TABLE_NAME_CHAT ORDER BY id DESC LIMIT 1")
    fun getLastChat(): Flow<ChatEntity>
}