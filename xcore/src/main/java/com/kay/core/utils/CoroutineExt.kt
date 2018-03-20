package com.kay.core.utils

import com.kay.core.BuildConfig
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Kay Tran on 20/3/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */

/**
 * forward the exception
 */
val exceptionThrower: CoroutineContext = CoroutineExceptionHandler { _, throwable ->
    throw throwable
}

/**
 * Take
 */
fun BaseRepository.withRetryExceptionHandler(_retry: Retry): CoroutineContext {
    return CoroutineExceptionHandler { _, e ->
        if (BuildConfig.DEBUG) e.printStackTrace()
        // ensure to work on the main thread.
        requestStateEvent.postValue(RequestState.ERROR(e))
        retryEvent.postValue(_retry)
    }
}