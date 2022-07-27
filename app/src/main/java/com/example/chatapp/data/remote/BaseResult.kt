package com.example.chatapp.data.remote

import okhttp3.ResponseBody

sealed class BaseResult<out T : Any> {
    data class Success<out T: Any>(val data: T) : BaseResult<T>()
    data class Error(val e: ResponseBody?) : BaseResult<Nothing>()
}


