package com.kay.core.network

/**
 * Created by none on 24/2/18.
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