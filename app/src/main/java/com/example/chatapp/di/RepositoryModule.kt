package com.example.chatapp.di

import com.example.chatapp.data.ChatRoomRepository
import com.example.chatapp.data.ChatRoomRepositoryImpl
import com.example.chatapp.data.FriendRepository
import com.example.chatapp.data.FriendRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideChatRoomRepository(chatRoomRepositoryImpl: ChatRoomRepositoryImpl): ChatRoomRepository

    @Binds
    @Singleton
    abstract fun provideFriendRepository(friendRepositoryImpl: FriendRepositoryImpl): FriendRepository

}