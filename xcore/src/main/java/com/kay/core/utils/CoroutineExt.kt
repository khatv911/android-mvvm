package com.kay.core.utils

import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Kay Tran on 20/3/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */


val exceptionThrower: CoroutineContext = CoroutineExceptionHandler { _, throwable ->
    throw throwable
}

fun BaseRepository.withRetryExceptionHandler(_retry: Retry): CoroutineContext {
    return CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        requestStateEvent.value = RequestState.ERROR(e)
        retryEvent.value = _retry
    }
}