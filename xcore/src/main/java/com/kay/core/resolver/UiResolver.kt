package com.kay.core.resolver

import com.kay.core.R


/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
interface UiResolver {

    /**
     * show error message with retryEvent option
     * @message: the error message to show
     * @retryOption: 1st param to indicate should we show the retryEvent (otherwise show dismiss), 2nd param is the action string
     */
    fun resolveErrorMessage(message: String, retryOption: Pair<Boolean, Int?> = (false to R.string.dismiss))

    /**
     * show error message with retryEvent option
     * @message: the error message to show
     * @retryOption: 1st param to indicate should we show the retryEvent (otherwise show dismiss), 2nd param is the action string
     */
    fun resolveErrorMessage(resource: Int, retryOption: Pair<Boolean, Int?> = (false to R.string.dismiss))

    /**
     * show connectivity indicator
     */
    fun showConnectivity(available: Boolean)


    /**
     * page or resource not found
     */
    fun resolveNotFound(retryOption: Pair<Boolean, Int?> = (false to R.string.dismiss))

    /**
     * handle un-auth cases ( token expired, login failed)
     */
    fun resolveUnAuthorized()

    /**
     * handle forbidden cases
     */
    fun resolveForbidden()


    fun showSuccess(message: String?)


}