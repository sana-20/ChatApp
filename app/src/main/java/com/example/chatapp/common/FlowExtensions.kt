package com.example.chatapp.common

import com.example.chatapp.data.remote.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

inline fun <reified T : Any, reified R : Any> BaseResult<T>.map(transform: (T) -> R): BaseResult<R> {
    return when (this) {
        is BaseResult.Success -> BaseResult.Success(transform(data))
        is BaseResult.Error -> this
    }
}

fun <T1, T2, T3, R> zip(
    first: Flow<T1>,
    second: Flow<T2>,
    third: Flow<T3>,
    transform: suspend (T1, T2, T3) -> R
): Flow<R> =
    first.zip(second) { a, b -> a to b }
        .zip(third) { (a, b), c ->
            transform(a, b, c)
        }