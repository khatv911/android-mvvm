package com.kay.core.error

import retrofit2.HttpException

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */

/**
 * Resolve Http exception
 */
interface HttpResolution {
    fun onHttpException(httpException: HttpException)
}

/**
 * The fallback resolution for all un-handled case
 */
interface GenericResolution {
    /**
     *
     */
    fun onGenericException(throwable: Throwable)
}

interface ConnectivityResolution {
    /**
     *
     */
    fun onConnectivityAvailable()

    /**
     *
     */
    fun onConnectivityUnavailable()
}


interface Resolution : HttpResolution, GenericResolution, ConnectivityResolution {
    fun resolve(throwable: Throwable)
}