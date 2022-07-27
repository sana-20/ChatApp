package com.example.chatapp.data

import com.example.chatapp.data.remote.ApiService
import com.example.chatapp.data.remote.FriendModel
import com.example.chatapp.data.remote.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : FriendRepository {
    override suspend fun getFriends(type: String): Flow<BaseResult<List<FriendModel>>> {
        return flow {
            val response = apiService.getFriend(type)
            if (response.isSuccessful) {
                emit(BaseResult.Success(response.body()!!.data))
            } else {
                emit(BaseResult.Error(response.errorBody()))
            }
        }
    }

}