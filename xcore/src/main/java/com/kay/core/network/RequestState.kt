package com.kay.core.network

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
sealed class RequestState {
    /**
     * the request is started
     */
    object STARTED : RequestState()

    /**
     * The request is done, either it's a success or an error
     */
    open class DONE : RequestState()

    /**
     * the request is done with successful response.
     */
    data class SUCCESS(val message: String?) : DONE()

    /**
     * The request is error
     */
    data class ERROR(val throwable: Throwable) : DONE()
}