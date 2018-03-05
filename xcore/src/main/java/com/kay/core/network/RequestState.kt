package com.kay.core.network

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
sealed class RequestState {
    /**
     * the request is running
     */
    object FETCHING : RequestState()

    /**
     * the request is done with successful response.
     */
    data class SUCCESS(val message: String?) : RequestState()

    /**
     * The request is error
     */
    data class ERROR(val throwable: Throwable) : RequestState()
}